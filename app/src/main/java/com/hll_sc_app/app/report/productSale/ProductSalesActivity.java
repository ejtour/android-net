package com.hll_sc_app.app.report.productSale;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.github.mikephil.charting.charts.PieChart;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.product.ProductSalesParam;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Bean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.TitleBar;

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
    private ProductSalesAdapter mAdapter;
    private IProductSalesContract.IProductSalesPresenter mPresenter;
    private final ProductSalesParam mParam = new ProductSalesParam();
    private TextView mSaleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_product_sales);
        ButterKnife.bind(this);
        StatusBarCompat.setTranslucent(getWindow(), true);
        showStatusBar();
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ProductSalesPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
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
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mTitleBar.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(this);
        }
    }

    @OnClick({R.id.rps_tag_custom, R.id.rps_tag_last_month, R.id.rps_tag_this_month, R.id.rps_tag_this_week})
    public void onViewClicked(TextView view) {
        ButterKnife.apply(mBtnList, (v, index) -> {
            v.setBackgroundResource(0);
            v.setTextColor(Color.WHITE);
        });
        view.setBackgroundResource(R.drawable.bg_tag_white_solid);
        view.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mParam.setEndDate(null);
        mParam.setStartDate(null);
        switch (view.getId()) {
            case R.id.rps_tag_custom:
                mParam.setDateFlag(4);
                Date endDate = new Date();
                mParam.setEndDate(endDate);
                mParam.setStartDate(CalendarUtils.getDateBefore(endDate, 29));
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
    }

    @Override
    public void showTop10(List<ProductSaleTop10Bean> resp) {
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
