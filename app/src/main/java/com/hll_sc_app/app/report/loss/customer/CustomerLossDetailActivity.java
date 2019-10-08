package com.hll_sc_app.app.report.loss.customer;

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
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossItem;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossReq;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossResp;
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
 * 客户流失率明细
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_CUSTOMER_LOSS_DETAIL)
public class CustomerLossDetailActivity extends BaseLoadActivity implements CustomerLossDetailContract.ICustomerLossDetailView, BaseQuickAdapter.OnItemClickListener {

    private static final int COLUMN_NUM = 5;
    private static final int[] WIDTH_ARRAY = {90,100,100, 100, 100};
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.report_date_arrow)
    ImageView dateArrow;
    @BindView(R.id.report_filter_arrow)
    ImageView filterArrow;
    @BindView(R.id.txt_date_name)
    TextView dateTextView;
    @BindView(R.id.txt_filter_flag)
    TextView lossFilterTextView;
    private CustomerLossDetailContract.ICustomerLossDetailPresenter mPresenter;
    CustomerAndShopLossReq mParam = new CustomerAndShopLossReq();
    private ContextOptionsWindow mExportOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mOptionsWindow;
    private String startDate = "";
    private String endDate = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_customer_loss_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CustomerLossDetailPresenter.newInstance();
        Date date = new Date();
        String currentDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
        startDate = CalendarUtils.format(CalendarUtils.getWantDay(date, -7),CalendarUtils.FORMAT_LOCAL_DATE);
        endDate = currentDate;
        dateTextView.setText(
                String.format("%s - %s",
                        CalendarUtils.getDateFormatString(startDate,CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD),
                        CalendarUtils.getDateFormatString(currentDate,CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)));
        //流失类型 0-近七天流失 1-近30天流失
        mParam.setDataType(0);
        //0-客户流失率 1-门店明细
        mParam.setFlag(0);
        mParam.setStartDate(startDate);
        mParam.setEndDate(endDate);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMoreCustomerLossDetail();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryCustomerLossDetail(true);
            }
        });
    }


    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.rog_title_bar,R.id.txt_filter_flag,R.id.txt_date_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rog_title_bar:
               showExportOptionsWindow(mTitleBar);
                break;
            case R.id.txt_filter_flag:
                showCustomerLossWindow(lossFilterTextView);
                break;
            case R.id.txt_date_name:
                showDateRangeWindow();
                break;
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
                    mPresenter.queryCustomerLossDetail(true);
                }
            });
            Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
            start.setTime(CalendarUtils.parse(startDate,CalendarUtils.FORMAT_SERVER_DATE));
            end.setTime(CalendarUtils.parse(endDate,CalendarUtils.FORMAT_SERVER_DATE));
            mDateRangeWindow.setSelectCalendarRange(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DATE),
                    end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE));

        }
        mDateRangeWindow.setOnDismissListener(()->{
            dateArrow.setRotation(0);
        });
        dateArrow.setRotation(180);
        mDateRangeWindow.showAsDropDownFix(dateTextView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        Utils.bindEmail(this, (email) -> mPresenter.exportCustomerLossDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportCustomerLossDetail(email, reqParams);
    }


    @Override
    public CustomerAndShopLossReq getRequestParams(){
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

    /**
     * 显示过滤窗口
     * @param view
     */
    private void showCustomerLossWindow(View view){
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_PRE_SEVEN_LOSS));
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_PRE_THIRTY_LOSS));
        mOptionsWindow.setOnDismissListener(()->{
            filterArrow.setRotation(0);
        });
        filterArrow.setRotation(180);
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
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
        ExcelRow row = new ExcelRow(this);
        if(isDisPlay) {
            row.updateChildView(COLUMN_NUM);
            ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
            array[0] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[0]));
            array[1] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[1]));
            array[2] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[2]));
            array[3] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[3]));
            array[4] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[4]));
            row.updateItemData(array);
            row.updateRowDate( "日期", "下单门店数", "流失门店数", "新增流失门店数", "流失率");
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
        return array;
    }


    @Override
    public void showCustomerLossDetail(CustomerAndShopLossResp lossResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(lossResp.getRecords()) && lossResp.getRecords().size() ==20);
        List<List<CharSequence>> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(lossResp.getRecords())) {
            for(CustomerAndShopLossItem item:lossResp.getRecords()){
                item.setType(0);
            }
            mExcel.setData(lossResp.getRecords(), append);
            mExcel.setHeaderView(generateHeader(true));
        }else{
            mExcel.setData(new ArrayList<>(), append);
            mExcel.setHeaderView(generateHeader(append));
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        String text =  OptionType.OPTION_PRE_SEVEN_LOSS;
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_EXPORT_DETAIL_INFO)){
            return;
        }
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_PRE_SEVEN_LOSS)){
            //7日流失
            mParam.setDataType(0);
        }
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_PRE_THIRTY_LOSS)){
            //30日流失
            mParam.setDataType(1);
            text = OptionType.OPTION_PRE_THIRTY_LOSS;
        }
        lossFilterTextView.setText(text);
        mPresenter.queryCustomerLossDetail(true);
        mOptionsWindow.dismiss();
    }
}
