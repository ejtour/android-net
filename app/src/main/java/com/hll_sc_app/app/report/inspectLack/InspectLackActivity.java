package com.hll_sc_app.app.report.inspectLack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.inspectLack.InspectLackResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chukun
 * @since 2019/08/15
 */

@Route(path = RouterConfig.REPORT_INSPECT_LACK_LIST)
public class InspectLackActivity extends BaseLoadActivity implements IInspectLackContract.IInspectLackView {
    private static final int COLUMN_NUM = 8;
    private static final int[] WIDTH_ARRAY = {120, 80, 80, 80, 80, 80, 80,80};
    @BindView(R.id.txt_date_name)
    TextView mTimeText;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rl_select_date)
    RelativeLayout mRlSelectDate;
    @BindView(R.id.txt_options)
    ImageView textOption;
    private IInspectLackContract.IInspectLackPresenter mPresenter;
    BaseReportReqParam mParam = new BaseReportReqParam();
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_inspect_lack);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = InspectLackPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        Date date = new Date();
        String endDateStr = CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD);
        String startDateStr = CalendarUtils.format(CalendarUtils.getWantDay(date,-29),Constants.SLASH_YYYY_MM_DD);
        mTimeText.setText(String.format("%s - %s", startDateStr, endDateStr));
        mParam.setStartDate(CalendarUtils.getDateFormatString(startDateStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_LOCAL_DATE));
        mParam.setEndDate(CalendarUtils.getDateFormatString(endDateStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_LOCAL_DATE));
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadInspectLackList();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.txt_date_name, R.id.img_back, R.id.txt_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date_name:
                showDateRangeWindow();
                break;
            case R.id.txt_options:
                showExportOptionsWindow(textOption);
            default:
                break;
        }
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
        Utils.bindEmail(this, (email) -> mPresenter.exportInspectLack(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportInspectLack(email, reqParams);
    }

    public BaseReportReqParam getRequestParams(){
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
        ExcelRow row = new ExcelRow(this);
        if(isDisPlay) {
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
            row.updateItemData(array);
            row.updateRowDate("日期", "收货单数", "收货金额", "原发货金额", "收货差异商品数", "收货差异量", "收货差异金额", "收货差异率");
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private View generatorFooter(InspectLackResp inspectLackResp,boolean isDisplay){
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
            row.updateItemData(array);
            row.updateRowDate("合计", inspectLackResp.getTotalInspectionOrderNum(), CommonUtils.formatMoney(Double.parseDouble(inspectLackResp.getTotalInspectionLackAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(inspectLackResp.getTotalOriDeliveryTradeAmount())),
                    "--", CommonUtils.formatNumber(inspectLackResp.getTotalInspectionLackNum()),
                    CommonUtils.formatMoney(Double.parseDouble(inspectLackResp.getTotalInspectionLackAmount())), CommonUtils.formatNumber(new BigDecimal(inspectLackResp.getTotalInspectionLackRate()).multiply(BigDecimal.valueOf(100)).toPlainString()) + "%");
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
        return array;
    }

    private void showDateRangeWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    mTimeText.setText(null);
                    mTimeText.setTag(R.id.date_start, "");
                    mTimeText.setTag(R.id.date_end, "");
                    mPresenter.loadInspectLackList();
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), Constants.SLASH_YYYY_MM_DD);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), Constants.SLASH_YYYY_MM_DD);
                    mTimeText.setText(String.format("%s-%s", startStr, endStr));
                    mTimeText.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mTimeText.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    String startDate = CalendarUtils.getDateFormatString(startStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_SERVER_DATE);
                    String endDate = CalendarUtils.getDateFormatString(endStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_SERVER_DATE);
                    mParam.setStartDate(startDate);
                    mParam.setEndDate(endDate);
                    mPresenter.loadInspectLackList();
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
    }

    @Override
    public void setInspectList(InspectLackResp inspectLackResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(inspectLackResp.getRecords()) && inspectLackResp.getRecords().size() == 20);
        if (!CommonUtils.isEmpty(inspectLackResp.getRecords())) {
            mExcel.setData(inspectLackResp.getRecords(), append);
            mExcel.setFooterView(generatorFooter(inspectLackResp, true));
            mExcel.setHeaderView(generateHeader(true));
        } else {
            mExcel.setData(new ArrayList<>(), append);
            mExcel.setFooterView(generatorFooter(inspectLackResp, append));
            mExcel.setHeaderView(generateHeader(append));
        }
    }
}
