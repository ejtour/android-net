package com.hll_sc_app.app.report.productsale;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.filter.ProductSalesParam;
import com.hll_sc_app.bean.report.resp.product.ProductCategory;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Bean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.PieMarker;
import com.hll_sc_app.widget.report.ReportEmptyView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/24
 */
@Route(path = RouterConfig.REPORT_PRODUCT_SALES_STATISTICS)
public class ProductSalesActivity extends BaseLoadActivity implements IProductSalesContract.IProductSalesView, TabLayout.OnTabSelectedListener {
    @BindView(R.id.rps_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rps_tag_custom)
    TextView mTagCustom;
    @BindView(R.id.rps_tag_last_month)
    TextView mTagLastMonth;
    @BindView(R.id.rps_tag_this_month)
    TextView mTagThisMonth;
    @BindView(R.id.rps_tag_this_week)
    TextView mTagThisWeek;
    @BindView(R.id.rps_sku_num)
    TextView mSkuNum;
    @BindView(R.id.rps_sales_num)
    TextView mSalesNum;
    @BindView(R.id.rps_sales_amount)
    TextView mSalesAmount;
    @BindView(R.id.rps_category_num)
    TextView mCategoryNum;
    @BindView(R.id.rps_pie_chart)
    PieChart mPieChart;
    @BindView(R.id.rps_tab)
    TabLayout mTab;
    @BindView(R.id.rps_list_view)
    RecyclerView mListView;
    @BindViews({R.id.rps_tag_custom, R.id.rps_tag_last_month, R.id.rps_tag_this_month, R.id.rps_tag_this_week})
    List<TextView> mBtnList;
    @BindView(R.id.rps_date_range)
    TextView mDateRange;
    @BindView(R.id.rps_pie_empty)
    ReportEmptyView mChartEmpty;
    @BindView(R.id.rps_list_empty)
    ReportEmptyView mListEmpty;
    private ProductSalesAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private IProductSalesContract.IProductSalesPresenter mPresenter;
    private final ProductSalesParam mParam = new ProductSalesParam();
    private TextView mSaleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_product_sales);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void initSystemBar() {
        StatusBarUtil.setTranslucent(this);
    }

    private void initData() {
        mPresenter = ProductSalesPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        StatusBarUtil.fitSystemWindowsWithPaddingTop(mTitleBar);
        mListView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new ProductSalesAdapter();
        View header = LayoutInflater.from(this).inflate(R.layout.item_product_sales, mListView, false);
        header.setBackgroundResource(R.color.base_activity_bg);
        header.<TextView>findViewById(R.id.ips_name).setTextColor(ContextCompat.getColor(this, R.color.color_999999));
        header.<TextView>findViewById(R.id.ips_rank).setTextColor(ContextCompat.getColor(this, R.color.color_999999));
        header.<TextView>findViewById(R.id.ips_spec).setTextColor(ContextCompat.getColor(this, R.color.color_999999));
        mSaleType = header.findViewById(R.id.ips_sales);
        mSaleType.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
        mAdapter.addHeaderView(header);
        mListView.setAdapter(mAdapter);
        mTab.addOnTabSelectedListener(this);
        initChart();
    }

    private void initChart() {
        mPieChart.setUsePercentValues(true); // 用百分比值
        mPieChart.getDescription().setEnabled(false); // 禁用图表底部描述
        mPieChart.setDrawHoleEnabled(false); // 允许花中心圆
        mPieChart.setTransparentCircleColor(Color.WHITE); // 设置透明圆颜色
        mPieChart.setTransparentCircleAlpha(110); // 设置透明圆透明度
        mPieChart.setTransparentCircleRadius(42.6f); // 设置透明圆半径
        mPieChart.setHighlightPerTapEnabled(true); // 点击高亮
        mPieChart.setDrawEntryLabels(false); // 禁用饼描述
        mPieChart.setEntryLabelTextSize(0);
        mPieChart.animateY(1400, Easing.EaseInOutQuad); // 动画展示

        mPieChart.setMarker(new PieMarker(this, mPieChart));

        Legend legend = mPieChart.getLegend();
        legend.setTextSize(10);
        legend.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }

    private void setChartData(List<ProductCategory> list) {
        List<PieEntry> entryList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (ProductCategory category : list) {
                entryList.add(new PieEntry(category.getCategoryOrderAmount(), category.getCategoryName()));
            }
            mCategoryNum.setText(String.format("(%s)", list.size()));
            mChartEmpty.setVisibility(View.GONE);
        } else {
            mCategoryNum.setText("(0)");
            mChartEmpty.setVisibility(View.VISIBLE);
        }
        PieDataSet dataSet = new PieDataSet(entryList, "");
        dataSet.setSliceSpace(2); // 设置饼图每个饼之间的间隔
        dataSet.setAutomaticallyDisableSliceSpacing(true);
        dataSet.setSelectionShift(6); // 设置高亮的饼突出大小
        List<Integer> colorList = Arrays.asList(ColorStr.CHART_COLOR_ARRAY);
        dataSet.setColors(colorList); // 设置颜色组
        dataSet.setDrawValues(false);
        PieData pieData = new PieData(dataSet);
        mPieChart.setData(pieData);
        mPieChart.highlightValues(null); // 取消高亮
        mPieChart.invalidate();
    }

    private void showDateRangeWindow(View view) {
        if (mDateRangeWindow == null) {
            Date endTime = new Date();
            updateSelectedDate(CalendarUtils.getDateBefore(endTime, 29), endTime);
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                if (start == null || end == null) {
                    end = new Date();
                    start = CalendarUtils.getDateBefore(end, 29);
                }
                updateSelectedDate(start, end);
                mPresenter.start();
            });
            mDateRangeWindow.setSelectCalendarRange(mParam.getStartDate(), mParam.getEndDate());
        }
        mDateRangeWindow.showAsDropDown(view, 0, UIUtils.dip2px(5));
        mDateRange.setVisibility(View.VISIBLE);
    }

    private void updateSelectedDate(Date startDate, Date endDate) {
        mParam.setEndDate(endDate);
        mParam.setStartDate(startDate);
        mDateRange.setText(String.format("%s - %s",
                CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick({R.id.rps_tag_custom, R.id.rps_tag_last_month, R.id.rps_tag_this_month, R.id.rps_tag_this_week})
    public void onViewClicked(TextView view) {
        mDateRange.setVisibility(View.GONE);
        ButterKnife.apply(mBtnList, (v, index) -> {
            v.setBackgroundResource(0);
            v.setTextColor(Color.WHITE);
        });
        view.setBackgroundResource(R.drawable.bg_tag_white_solid);
        view.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        switch (view.getId()) {
            case R.id.rps_tag_custom:
                mParam.setDateFlag(4);
                showDateRangeWindow(view);
                break;
            case R.id.rps_tag_last_month:
                mParam.setDateFlag(3);
                break;
            case R.id.rps_tag_this_month:
                mParam.setDateFlag(2);
                break;
            case R.id.rps_tag_this_week:
                mParam.setDateFlag(1);
                break;
        }
        mPresenter.start();
    }

    @Override
    public void showProductSales(ProductSaleResp resp) {
        mSalesNum.setText(CommonUtils.formatNum(resp.getOrderNum()));
        mSkuNum.setText(CommonUtils.formatNum(resp.getSkuNum()));
        mSalesAmount.setText(CommonUtils.formatMoney(resp.getOrderAmount()));
        setChartData(resp.getProductCategorySaleVo());
    }

    @Override
    public void showTop10(List<ProductSaleTop10Bean> resp) {
        mListEmpty.setVisibility(CommonUtils.isEmpty(resp) ? View.VISIBLE : View.GONE);
        mAdapter.setNewData(resp, mParam.getType());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mParam.setType(tab.getPosition() + 1);
        mPresenter.queryProductSalesTop10();
        mSaleType.setText(tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
