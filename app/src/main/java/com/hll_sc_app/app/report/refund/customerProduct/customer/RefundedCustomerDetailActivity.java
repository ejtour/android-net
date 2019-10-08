package com.hll_sc_app.app.report.refund.customerProduct.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.refund.RefundedCustomerReq;
import com.hll_sc_app.bean.report.refund.RefundedCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;
import com.hll_sc_app.bean.report.search.SearchResultItem;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
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
 * 退货客户明细统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_REFUNDED_CUSTOMER_DETAIL)
public class RefundedCustomerDetailActivity extends BaseLoadActivity implements RefundedCustomerDetailContract.IRefundedCustomerDetailView, BaseQuickAdapter.OnItemClickListener {
    private static final int REFUND_CUSTOMERT_CODE = 11002;
    private static final int COLUMN_NUM = 9;
    private static final int[] WIDTH_ARRAY = {150,120,80, 80, 60, 60,60,60,60};
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    private RefundedCustomerDetailContract.IRefundedCustomerDetailPresenter mPresenter;
    RefundedCustomerReq mParam = new RefundedCustomerReq();
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.txt_filter_flag)
    TextView depositTextView;
    @BindView(R.id.report_date_arrow)
    ImageView dateArrow;
    @BindView(R.id.report_filter_arrow)
    ImageView filterArrow;
    @BindView(R.id.txt_date_name)
    TextView dateTextView;
    private ContextOptionsWindow mExportOptionsWindow;
    private ContextOptionsWindow mOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private String startDate = "";
    private String endDate = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refunded_customer_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = RefundedCustomerDetailPresenter.newInstance();
        Date date = new Date();
        String currentDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
        long startDate = DateUtil.getMonthFirstDay(0,Long.valueOf(currentDate));
        dateTextView.setText(
                String.format("%s - %s",
                        CalendarUtils.getDateFormatString(startDate+"",CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD),
                        CalendarUtils.getDateFormatString(currentDate,CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)));
        //设置包含押金商品
        mParam.setSign(1);
        mParam.setStartDate(startDate+"");
        mParam.setEndDate(currentDate);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMoreRefundedCustomerDetail();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryRefundedCustomerDetail(true);
            }
        });
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
            filterArrow.setRotation(0);
        });
        filterArrow.setRotation(180);
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }


    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.rog_title_bar,R.id.img_clear,R.id.txt_filter_flag,R.id.txt_date_name,R.id.edt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                edtSearch.setText("");
                mPresenter.queryRefundedCustomerDetail(true);
                imgClear.setVisibility(View.GONE);
                break;
            case R.id.rog_title_bar:
               showExportOptionsWindow(mTitleBar);
                break;
            case R.id.txt_filter_flag:
                showRefundedFilterWindow(depositTextView);
                break;
            case R.id.txt_date_name:
                showDateRangeWindow();
                break;
            case R.id.edt_search:
                RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_SEARCH, this, REFUND_CUSTOMERT_CODE);
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
                    dateTextView.setText(null);
                    dateTextView.setTag(R.id.date_start, "");
                    dateTextView.setTag(R.id.date_end, "");
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), Constants.SLASH_YYYY_MM_DD);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), Constants.SLASH_YYYY_MM_DD);
                    dateTextView.setText(String.format("%s-%s", startStr, endStr));
                    dateTextView.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    dateTextView.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    startDate = CalendarUtils.getDateFormatString(startStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_SERVER_DATE);
                    endDate = CalendarUtils.getDateFormatString(endStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_SERVER_DATE);
                    mPresenter.queryRefundedCustomerDetail(true);
                }
            });
        }
        mDateRangeWindow.setOnDismissListener(()->{
            dateArrow.setRotation(0);
        });
        dateArrow.setRotation(180);
        mDateRangeWindow.showAsDropDownFix(dateTextView);
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
        Utils.bindEmail(this, (email) -> mPresenter.exportRefundedCustomerDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportRefundedCustomerDetail(email, reqParams);
    }


    @Override
    public RefundedCustomerReq getRequestParams(){
        if (dateTextView.getTag(R.id.date_start) != null) {
            startDate = (String) dateTextView.getTag(R.id.date_start);
            mParam.setStartDate(startDate);
        }
        if(dateTextView.getTag(R.id.date_end) != null){
            endDate = (String) dateTextView.getTag(R.id.date_end);
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

    private View generateHeader(boolean isDisPlay) {
        View view = View.inflate(this,R.layout.item_report_refunded_customer_detail_header,null);
        if(isDisPlay) {
            mExcel.setHeaderView(view);
        }
        return view;
    }

    private View generatorFooter(RefundedCustomerResp refundCustomerResp, boolean isDisplay){
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
            row.updateItemData(array);
            row.updateRowDate("合计", "----", refundCustomerResp.getTotalRefundBillNum()+"",
                    refundCustomerResp.getTotalRefundProductNum()+"",
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalCashAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalBankCardAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalOnLineAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalAccountAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalRefundAmount())));
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[8]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }


    @Override
    public void showRefundedCustomerDetail(RefundedCustomerResp refundCustomerResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(refundCustomerResp.getGroupVoList()) && refundCustomerResp.getGroupVoList().size() == 20);
        if (!CommonUtils.isEmpty(refundCustomerResp.getGroupVoList())) {
            mExcel.setData(refundCustomerResp.getGroupVoList(), append);
            mExcel.setHeaderView(generateHeader(true));
            mExcel.setFooterView(generatorFooter(refundCustomerResp, true));
        } else {
            mExcel.setData(new ArrayList<>(), append);
            generateHeader(append);
            mExcel.setFooterView(generatorFooter(refundCustomerResp, append));
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
        depositTextView.setText(text);
        mPresenter.queryRefundedCustomerDetail(true);
        mOptionsWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REFUND_CUSTOMERT_CODE && resultCode == RESULT_OK) {
            SearchResultItem bean = data.getParcelableExtra("result");
            edtSearch.setText(bean.getName());
            imgClear.setVisibility(View.VISIBLE);
            RefundedCustomerReq requestParams = getRequestParams();
            if(bean.getType()==0) {
                requestParams.setPurchaserID(bean.getShopMallId());
            }else{
                requestParams.setShopID(bean.getShopMallId());
            }
            mPresenter.queryRefundedCustomerDetail(true);
        }
    }
}
