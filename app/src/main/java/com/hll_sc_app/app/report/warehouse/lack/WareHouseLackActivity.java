package com.hll_sc_app.app.report.warehouse.lack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.PurchaserSelectWindow;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.lack.LackDetailsBean;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_WAREHOUSE_PRODUCT_DETAIL)
public class WareHouseLackActivity extends BaseLoadActivity implements IWareHouseLackContract.IWareHouseLackView {
    private static final int[] WIDTH_ARRAY = {40, 110, 150, 120, 190, 80, 100, 80, 80, 90, 100};
    @BindView(R.id.rwl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rwl_search_view)
    SearchView mSearchView;
    @BindView(R.id.rwl_date)
    TextView mDate;
    @BindView(R.id.rwl_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.rwl_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rwl_shipper_arrow)
    TriangleView mShipperArrow;
    @BindView(R.id.rwl_shipper)
    TextView mShipper;
    private IWareHouseLackContract.IWareHouseLackPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private PurchaserSelectWindow mPurchaserWindow;
    private List<PurchaserBean> mShipperList;
    private AtomicInteger mIndex = new AtomicInteger();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_warehouse_lack);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("billCategory", "2");
        mReq.put("groupID", UserConfig.getGroupID());
        Date date = new Date();
        mDate.setTag(R.id.date_start, date);
        mDate.setTag(R.id.date_end, date);
        updateSelectedDate();
        mPresenter = WareHouseLackPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        initExcel();
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(WareHouseLackActivity.this,
                        searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mReq.put("productName", searchContent);
                mPresenter.loadList();
            }
        });
    }

    private void initExcel() {
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setHeaderView(generateHeader());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
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
    public void exportReportID(String reportID, IExportView export) {
        Utils.exportReportID(this, reportID,export);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void cacheShipperList(List<PurchaserBean> purchaserBeans) {
        if (!CommonUtils.isEmpty(purchaserBeans)) {
            PurchaserBean bean = new PurchaserBean();
            bean.setPurchaserName("全部");
            purchaserBeans.add(0, bean);
        }
        mShipperList = purchaserBeans;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        mPresenter.export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("序号", "商品编号", "商品名称", "规格/单位", "货主", "订货量", "订货金额", "发货量", "缺货量", "缺货金额", "缺货率");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        for (int i = 1; i < 5; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL);
        }
        for (int i = 5; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    private void updateSelectedDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
        mReq.put("date", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
    }

    @OnClick(R.id.rwl_date_btn)
    public void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.loadList();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }


    @Override
    public void setData(LackDetailsResp resp, boolean append) {
        List<LackDetailsBean> records = resp.getRecords();
        if (!append) {
            mIndex.set(0);
        }
        if (!CommonUtils.isEmpty(records))
            for (LackDetailsBean bean : records) {
                bean.setNo(String.valueOf(mIndex.incrementAndGet()));
        }
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
    }

    @OnClick(R.id.rwl_shipper_btn)
    public void showPurchaserWindow(View view) {
        if (mShipperList == null) {
            mPresenter.getShipperList();
            return;
        }
        mShipperArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mShipper.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new PurchaserSelectWindow(this, mShipperList);
            mPurchaserWindow.setListener(bean -> {
                if (TextUtils.equals(bean.getPurchaserName(), "全部")) {
                    mShipper.setText("货主");
                } else {
                    mShipper.setText(bean.getPurchaserName());
                }
                mReq.put("shipperID", bean.getPurchaserID());
                mPresenter.loadList();
            });
            mPurchaserWindow.setOnDismissListener(() -> {
                mShipperArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mShipper.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mPurchaserWindow.showAsDropDownFix(view);
    }
}
