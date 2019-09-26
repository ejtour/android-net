package com.hll_sc_app.app.report.warehouse.serviceFee;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.PurchaserSelectWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.enums.ReportWareHouseServiceFeePayModelEnum;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryItem;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeItem;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeResp;
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
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_WAREHOUSE_SERVICE_FEE)
public class WareHouseServiceFeeActivity extends BaseLoadActivity implements IWareHouseServiceFeeContract.IWareHouseServiceFeeView {

    private static final int COLUMN_NUM = 7;
    private static final int[] WIDTH_ARRAY = {40,170,160, 120, 120, 120, 120};
    @BindView(R.id.rog_date)
    TextView mTimeText;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    private IWareHouseServiceFeeContract.IWareHouseServiceFeePresenter mPresenter;
    WareHouseServiceFeeReq mParam = new WareHouseServiceFeeReq();
    @BindView(R.id.rog_date_btn)
    View mRlSelectDate;
    @BindView(R.id.rog_purchaser)
    TextView mPurchaser;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private PurchaserSelectWindow mPurchaserWindow;
    AtomicInteger atomicInteger;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_warehouse_service_fee);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = WareHouseServiceFeePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        Date date = new Date();
        String startDateStr = CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD);
        String currentServerDate = CalendarUtils.getDateFormatString(startDateStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_LOCAL_DATE);
        String startServerDate = DateUtil.getMonthFirstDay(0,Long.valueOf(currentServerDate))+"";
        mTimeText.setText(String.format("%s", "日期筛选"));
        mParam.setStartDate(startServerDate);
        mParam.setEndDate(currentServerDate);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadWareHouseServiceFeeList();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.rog_date_btn,R.id.rog_purchaser_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rog_date_btn:
                showDateRangeWindow();
                break;
            case R.id.rog_purchaser_btn:
                toShipperList();
                break;
            default:
                break;
        }
    }

    private void toShipperList() {
        mPurchaser.setSelected(true);
        mPresenter.getShipperList("");
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
        Utils.bindEmail(this, (email) -> mPresenter.exportWareHouseServiceFeeList(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportWareHouseServiceFeeList(email, reqParams);
    }


    @Override
    public WareHouseServiceFeeReq getRequestParams(){
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
            row.updateItemData(array);
            row.updateRowDate("序号", "货主集团", "合作时长", "收费模式", "应收服务费", "已收服务费", "未收服务费");
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }


    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL );
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL );
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL);
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
                    mPresenter.loadWareHouseServiceFeeList();
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
    }


    @Override
    public void setWareHouseDeliveryServiceFeeList(WareHouseServiceFeeResp wareHouseServiceFeeResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(wareHouseServiceFeeResp.getDataSource()) && wareHouseServiceFeeResp.getDataSource().size() == 20);
        List<List<CharSequence>> list = new ArrayList<>();
        if(!append) {
             atomicInteger = new AtomicInteger(0);
        }
        if (!CommonUtils.isEmpty(wareHouseServiceFeeResp.getDataSource())) {
            for(WareHouseServiceFeeItem item: wareHouseServiceFeeResp.getDataSource()){
                item.setSequenceNo(atomicInteger.incrementAndGet());
            }
            mExcel.setData(wareHouseServiceFeeResp.getDataSource(), append);
            mExcel.setHeaderView(generateHeader(true));
        }else{
            mExcel.setData(new ArrayList<>(), append);
            mExcel.setHeaderView(generateHeader(append));
        }

    }

    @Override
    public void showPurchaserWindow(List<PurchaserBean> list) {
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new PurchaserSelectWindow((Activity) this, list);
            mPurchaserWindow.setListener(bean -> {
                if (TextUtils.equals(bean.getPurchaserName(), "全部")) {
                    mPurchaser.setText("货主");
                } else {
                    mPurchaser.setText(bean.getPurchaserName());
                }
                mPurchaser.setTag(bean.getPurchaserID());
                mPresenter.loadWareHouseServiceFeeList();
            });
            mPurchaserWindow.setOnDismissListener(() -> {
                mPurchaser.setSelected(false);
            });
        }
        mPurchaserWindow.showAsDropDownFix(mPurchaser);
    }

    @Override
    public String getShipperID() {
        String shipperID = null;
        if (mPurchaser != null && mPurchaser.getTag() != null) {
            shipperID = (String) mPurchaser.getTag();
        }
        return shipperID;
    }

    private List<CharSequence> convertToRowData(WareHouseServiceFeeItem item,AtomicInteger atomicInteger){
        List<CharSequence> list = new ArrayList<>();
        list.add(atomicInteger.incrementAndGet()+"");
        list.add(item.getShipperName()); // 货主集团
        list.add(
                String.format("%s - %s",
                        CalendarUtils.getDateFormatString(item.getStartDate()+"",CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD),
                        CalendarUtils.getDateFormatString(item.getEndDate()+"",CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)));// 合作时长
        list.add(ReportWareHouseServiceFeePayModelEnum.getServiceFeePayModeDescByCode(item.getTermType())); // 收费模式
        list.add(CommonUtils.formatMoney(Double.parseDouble(item.getTotalPrice()))); // 应收服务费
        list.add(CommonUtils.formatMoney(Double.parseDouble(item.getPaymentAmount()))); //已收服务费
        list.add(CommonUtils.formatMoney(Double.parseDouble(item.getUnPaymentAmount()))); // 未收服务费
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
