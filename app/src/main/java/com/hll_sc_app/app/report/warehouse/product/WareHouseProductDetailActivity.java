package com.hll_sc_app.app.report.warehouse.product;

import android.app.Activity;
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
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.PurchaserSelectWindow;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CustomerLackSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.event.CustomerLackSearchEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductItem;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_WAREHOUSE_PRODUCT_DETAIL)
public class WareHouseProductDetailActivity extends BaseLoadActivity implements IWareHouseProductDetailContract.IWareHouseProductDetailView {

    private static final int COLUMN_NUM = 11;
    private static final int[] WIDTH_ARRAY = {40,110,150, 120, 190, 80, 100, 80,80,90,100};
    @BindView(R.id.rog_date)
    TextView mTimeText;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    private IWareHouseProductDetailContract.IWareHouseProductDetailPresenter mPresenter;
    WareHouseLackProductReq mParam = new WareHouseLackProductReq();
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.rog_date_btn)
    View mRlSelectDate;
    @BindView(R.id.rog_purchaser_arrow)
    TriangleView mPurchaserArrow;
    @BindView(R.id.rog_purchaser)
    TextView mPurchaser;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    List<PurchaserBean> shipperBeans;
    private PurchaserSelectWindow mPurchaserWindow;;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_warehouse_product_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = WareHouseProductDetailPresenter.newInstance();
        mPresenter.register(this);
        EventBus.getDefault().register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        Date date = new Date();
        String startDateStr = CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD);
        mTimeText.setText(String.format("%s", startDateStr));
        mParam.setDate(CalendarUtils.getDateFormatString(startDateStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_LOCAL_DATE));
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadWareHouseProductDetailList();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.rog_date_btn, R.id.edt_search,R.id.img_clear,R.id.rog_purchaser_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rog_date_btn:
                showDateRangeWindow();
                break;
            case R.id.edt_search:
                SearchActivity.start("", CustomerLackSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                mParam.setProductName("");
                edtSearch.setText("");
                mPresenter.loadWareHouseProductDetailList();
                imgClear.setVisibility(View.GONE);
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


    @Subscribe
    public void onEvent(CustomerLackSearchEvent event) {
        String name = event.getSearchWord();
        if (!TextUtils.isEmpty(name)) {
            edtSearch.setText(name);
            mParam.setProductName(name);
            imgClear.setVisibility(View.VISIBLE);
        } else {
            mParam.setProductName("");
        }
        mPresenter.loadWareHouseProductDetailList();
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
        Utils.bindEmail(this, (email) -> mPresenter.exportWareHouseProductDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportWareHouseProductDetail(email, reqParams);
    }


    @Override
    public WareHouseLackProductReq getRequestParams(){
        //代仓维度
        mParam.setBillCategory(2);
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
            array[8] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[8]));
            array[9] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[9]));
            array[10] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[10]));
            row.updateItemData(array);
            row.updateRowDate("序号", "商品编号", "商品名称", "规格/单位", "货主", "订货量", "订货金额", "发货量","缺货量","缺货金额","缺货率");
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private View generatorFooter(InspectLackDetailResp inspectLackDetailResp, boolean isDisplay) {
        ExcelRow row = new ExcelRow(this);
        if (isDisplay) {
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
            row.updateRowDate("合计", "--", "--", "--", "--", "--",
                    CommonUtils.formatMoney(Double.parseDouble(inspectLackDetailResp.getTotalInspectionLackAmount())), CommonUtils.formatNumber(new BigDecimal(inspectLackDetailResp.getTotalInspectionLackRate()).multiply(BigDecimal.valueOf(100)).toPlainString()) + "%");
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[8]),Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[9] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[9]),Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[10] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[10]),Gravity.CENTER_VERTICAL | Gravity.RIGHT);
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
                    mPresenter.loadWareHouseProductDetailList();
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
                    mParam.setDate(startDate);
                    mParam.setEndDate(endDate);
                    mPresenter.loadWareHouseProductDetailList();
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
    }


    @Override
    public void setWareHouseProductDetailList(WareHouseLackProductResp wareHouseLackProductResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(wareHouseLackProductResp.getRecords()) && wareHouseLackProductResp.getRecords().size() == 20);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (!CommonUtils.isEmpty(wareHouseLackProductResp.getRecords())) {
            for (WareHouseLackProductItem bean : wareHouseLackProductResp.getRecords()) {
                bean.setIndex(atomicInteger.incrementAndGet());
            }
            mExcel.setData(wareHouseLackProductResp.getRecords(), append);
            mExcel.setHeaderView(generateHeader(true));
        } else {
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
                mPresenter.loadWareHouseProductDetailList();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
