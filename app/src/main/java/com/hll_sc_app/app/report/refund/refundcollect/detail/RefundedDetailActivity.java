package com.hll_sc_app.app.report.refund.refundcollect.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.refund.RefundedReq;
import com.hll_sc_app.bean.report.refund.RefundedResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退货明细统计
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_REFUNDED_COLLECT_DETAIL)
public class RefundedDetailActivity extends BaseLoadActivity implements RefundedDetailContract.IRefundedDetailView, BaseQuickAdapter.OnItemClickListener {

    private static final int COLUMN_NUM = 11;
    private static final int[] WIDTH_ARRAY = {90,100,100, 100, 60, 60,60,60,60,100,100};
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.txt_options)
    ImageView imageView;
    @BindView(R.id.txt_date_name)
    TextView textDateView;
    @BindView(R.id.txt_filter_flag)
    TextView textFilterView;
    @BindView(R.id.report_date_arrow)
    ImageView dateArrow;
    private RefundedDetailContract.IWaitRefundCustomerDetailPresenter mPresenter;
    RefundedReq mParam = new RefundedReq();
    private ContextOptionsWindow mExportOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private String startDate = "";
    private String endDate = "";
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refuned_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = RefundedDetailPresenter.newInstance();
        Date date = new Date();
        String currentDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
        long startDate = DateUtil.getMonthFirstDay(0,Long.valueOf(currentDate));
        textDateView.setText(
                String.format("%s - %s",
                        CalendarUtils.getDateFormatString(startDate+"",CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD),
                        CalendarUtils.getDateFormatString(currentDate,CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)));
        //设置包含押金商品
        mParam.setSign(1);
        mParam.setStartDate(startDate+"");
        mParam.setEndDate(currentDate);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMoreRefundedDetail();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryRefundedDetail(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.txt_options,R.id.txt_filter_flag,R.id.txt_date_name,R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_options:
                showExportOptionsWindow(imageView);
                break;
            case R.id.txt_filter_flag:
                showRefundedFilterWindow(textFilterView);
                break;
            case R.id.txt_date_name:
                showDateRangeWindow();
            default:
                break;
        }
    }

    /**
     * 显示时间组件
     */
    private void showDateRangeWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    textDateView.setText(null);
                    textDateView.setTag(R.id.date_start, "");
                    textDateView.setTag(R.id.date_end, "");
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), Constants.SLASH_YYYY_MM_DD);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), Constants.SLASH_YYYY_MM_DD);
                    textDateView.setText(String.format("%s-%s", startStr, endStr));
                    textDateView.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    textDateView.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    startDate = CalendarUtils.getDateFormatString(startStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_SERVER_DATE);
                    endDate = CalendarUtils.getDateFormatString(endStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_SERVER_DATE);
                    mPresenter.queryRefundedDetail(true);
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(textDateView);
    }


    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        Utils.bindEmail(this, (email) -> mPresenter.exportRefundedDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportRefundedDetail(email, reqParams);
    }


    @Override
    public RefundedReq getRequestParams(){
        if (textDateView.getTag(R.id.date_start) != null) {
            startDate = (String) textDateView.getTag(R.id.date_start);
            mParam.setStartDate(startDate);
        }
        if(textDateView.getTag(R.id.date_end) != null){
            endDate = (String) textDateView.getTag(R.id.date_end);
            mParam.setEndDate(endDate);
        }
        return mParam;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    /**
     * 显示过滤窗口
     * @param view
     */
    private void showRefundedFilterWindow(View view){
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        list.add(new OptionsBean(R.drawable.ic_menu_all, OptionType.OPTION_ALL));
        list.add(new OptionsBean(R.drawable.ic_menu_deposit, OptionType.OPTION_NOT_DEPOSIT));
        mOptionsWindow.setOnDismissListener(()->{
            dateArrow.setRotation(0);
        });
        dateArrow.setRotation(180);
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    private View generateHeader(boolean isDisPlay) {
        View view = View.inflate(this,R.layout.item_report_refunded_detail_header,null);
        if(isDisPlay) {
            mExcel.setHeaderView(view);
        }
        return view;
    }

    private View generatorFooter(RefundedResp refundedResp, boolean isDisplay){
        ExcelRow row = new ExcelRow(this);
        if(isDisplay) {
            row.updateChildView(COLUMN_NUM);
            ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
            array[0] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[0]));
            array[1] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[1]));
            array[2] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[2]));
            array[3] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[3]));
            array[4] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[4]));
            array[5] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[5]));
            array[6] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[6]));
            array[7] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[7]));
            array[8] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[8]));
            array[9] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[9]));
            array[10] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[10]));
            row.updateItemData(array);
            row.updateRowDate("合计", refundedResp.getTotalRefundBillNum()+"",
                    refundedResp.getTotalRefundGroupNum()+"/"+refundedResp.getTotalRefundShopNum(),
                    refundedResp.getTotalRefundProductNum()+"",
                    CommonUtils.formatMoney(Double.parseDouble(refundedResp.getTotalCashAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundedResp.getTotalBankCardAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundedResp.getTotalOnLineAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundedResp.getTotalAccountAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundedResp.getTotalRefundAmount())),
                    refundedResp.getTotalBillNum()+"",refundedResp.getTotalRefundProportion());
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[8]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[9] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[9]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[10] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[10]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }


    @Override
    public void showRefundedDetail(RefundedResp refundedResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(refundedResp.getGroupVoList()) && refundedResp.getGroupVoList().size() == 20);
        if (!CommonUtils.isEmpty(refundedResp.getGroupVoList())) {
            mExcel.setData(refundedResp.getGroupVoList(), append);
            mExcel.setHeaderView(generateHeader(true));
            mExcel.setFooterView(generatorFooter(refundedResp, true));
        } else {
            mExcel.setData(new ArrayList<>(), append);
            generateHeader(append);
            mExcel.setFooterView(generatorFooter(refundedResp, append));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        String text =  OptionType.OPTION_ALL;
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_EXPORT_DETAIL_INFO)){
            return;
        }
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_ALL)){
            //包含押金
            mParam.setSign(1);
        }
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_NOT_DEPOSIT)){
            //不包含押金
            mParam.setSign(2);
            text = OptionType.OPTION_NOT_DEPOSIT;
        }
        textFilterView.setText(text);
        mPresenter.queryRefundedDetail(true);
        mOptionsWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
