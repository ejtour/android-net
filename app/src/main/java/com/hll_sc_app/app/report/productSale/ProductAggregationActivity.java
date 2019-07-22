package com.hll_sc_app.app.report.productSale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.req.ProductSaleAggregationReq;
import com.hll_sc_app.bean.report.req.ProductSaleTopReq;
import com.hll_sc_app.bean.report.resp.product.ProductCategory;
import com.hll_sc_app.bean.report.resp.product.ProductSaleAggregationResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTopDetail;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品销售汇总
 * @author chukun
 */
@Route(path = RouterConfig.REPORT_PRODUCT_SALES_STATISTICS)
public class ProductAggregationActivity extends BaseLoadActivity implements IProductAggregationContract.IProductAggregationView {


    @BindView(R.id.product_sale_aggregation_header_product_value)
    TextView skuNumTextView;
    @BindView(R.id.product_sale_aggregation_header_salesnum_value)
    TextView salesNumTextView;
    @BindView(R.id.product_sale_aggregation_header_amount_value)
    TextView salesAmountTextView;
    @BindView(R.id.product_sale_aggregation_customer_time)
    TextView productSaleAggregationCustomerTime;
    @BindView(R.id.product_sales_pie_chart)
    PieChart productSalesPieChart;

    @BindView(R.id.item_product_sale_detail)
    RecyclerView mRecyclerView;

    private DateRangeWindow mDateRangeWindow;

    private IProductAggregationContract.IProductAggregationPresenter mPresenter;

    private ProductAggregationAdapter adapter;

    ProductSaleAggregationReq productSaleAggregationReq = new ProductSaleAggregationReq();
    ProductSaleTopReq productSaleTopReq = new ProductSaleTopReq();


    /**
     * 0 表示选中本周
     * 1 选中本月
     * 2 选中上月
     * 3 自定义时间
     */
    private int dateSelected = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_product_aggregation);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = ProductAggregationPresenter.newInstance();
        mPresenter.register(this);
        adapter = new ProductAggregationAdapter();
        mRecyclerView.setAdapter(adapter);
        initProductDatas();
    }

    /**
     * 初始化商品销量统计
     */
    private void initProductDatas(){
        //请求商品销售汇总

        byte dataFlag = (byte) 1;
        long startDate = DateUtil.getWeekFirstDay(0);
        long endDate = DateUtil.getWeekLastDay(0);
        productSaleAggregationReq.setDateFlag(dataFlag);
        productSaleAggregationReq.setStartDate(startDate);
        productSaleAggregationReq.setEndDate(endDate);
        productSaleAggregationReq.setGroupID(Long.valueOf(UserConfig.getGroupID()));
        mPresenter.queryProductSaleAggregation(productSaleAggregationReq);
        //请求商品销量top10
        productSaleTopReq.setDateFlag(dataFlag);
        productSaleTopReq.setStartDate(startDate);
        productSaleTopReq.setEndDate(endDate);
        //表示销量
        productSaleTopReq.setType((byte)1);
        productSaleTopReq.setGroupID(Long.valueOf(UserConfig.getGroupID()));
        mPresenter.queryProductSaleTop(productSaleTopReq);
    }

    @Override
    public void setProductAggregation(ProductSaleAggregationResp resp) {
        skuNumTextView.setText(resp.getSkuNum() + "");
        salesNumTextView.setText(resp.getOrderNum() + "");
        salesAmountTextView.setText(resp.getOrderAmount() + "");
        List<ProductCategory> productCategories = resp.getProductCategorySaleVo();
        double totalAmount = 0.0d;
        List<PieEntry> pieEntries = new ArrayList<>();
        for (ProductCategory p : productCategories) {
            totalAmount += p.getCategoryOrderAmount();
        }
        for (ProductCategory p : productCategories) {
            pieEntries.add(new PieEntry(CommonUtils.divDouble(p.getCategoryOrderAmount(), totalAmount).floatValue(), p.getCategoryName()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        PieData pieData = new PieData(pieDataSet);
        productSalesPieChart.setData(pieData);
    }

    @Override
    public void setProductTopList(List<ProductSaleTopDetail> details) {
        adapter.setNewData(details);
    }

    /**
     * 初始化日期弹窗
     */
    private void initDateWindow(){
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    productSaleAggregationReq.setGroupID(Long.valueOf(UserConfig.getGroupID()));
                    productSaleAggregationReq.setStartDate(Long.valueOf(CalendarUtils.toLocalDate(calendarStart.getTime())));
                    productSaleAggregationReq.setEndDate(Long.valueOf(CalendarUtils.toLocalDate(calendarEnd.getTime())));
                    productSaleAggregationReq.setDateFlag((byte) 4);
                    productSaleTopReq.setGroupID(Long.valueOf(UserConfig.getGroupID()));
                    productSaleTopReq.setStartDate(Long.valueOf(CalendarUtils.toLocalDate(calendarStart.getTime())));
                    productSaleTopReq.setEndDate(Long.valueOf(CalendarUtils.toLocalDate(calendarEnd.getTime())));
                    productSaleTopReq.setDateFlag((byte) 4);
                    //
                    dateSelected = 3;
                    mPresenter.queryProductSaleAggregation(productSaleAggregationReq);
                    mPresenter.queryProductSaleTop(productSaleTopReq);
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                productSaleAggregationCustomerTime.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.product_sale_aggregation_customer_time)
    public void selectDate(View view) {
        initDateWindow();
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @OnClick({R.id.product_sale_aggregation_current_week, R.id.product_sale_aggregation_current_month,
              R.id.product_sale_aggregation_last_month,R.id.product_sale_list_num_header, R.id.product_sale_list_amount_header})
    public void onViewClicked(View view) {
        long startDate = 0L;
        long endDate = 0L;
        byte dataFlag = 0;
        byte type = 1;
        boolean isOnlyTopReq = false;
        switch (view.getId()) {
            case R.id.product_sale_aggregation_current_week:
                dataFlag = (byte) 1;
                startDate = DateUtil.getWeekFirstDay(0);
                endDate = DateUtil.getWeekLastDay(0);
                dateSelected = 0 ;
                break;
            case R.id.product_sale_aggregation_current_month:
                productSaleAggregationReq.setDateFlag((byte) 2);
                dataFlag = (byte) 2;
                startDate = DateUtil.getMonthFirstDay(0);
                endDate = DateUtil.getMonthLastDay(0);
                dateSelected = 1 ;
                break;
            case R.id.product_sale_aggregation_last_month:
                productSaleAggregationReq.setDateFlag((byte) 3);
                dataFlag = (byte) 3;
                startDate = DateUtil.getMonthFirstDay(-1);
                endDate = DateUtil.getMonthLastDay(-1);
                dateSelected = 2 ;
                break;
            case R.id.product_sale_list_num_header:
                adapter.setSalesNum(true);
                isOnlyTopReq = true;
                type = 1;
                break;
            case R.id.product_sale_list_amount_header:
                adapter.setSalesNum(false);
                isOnlyTopReq = true;
                type = 1;
                break;
        }

        if(!isOnlyTopReq) {
            //封装查询 商品销售汇总
            productSaleAggregationReq.setDateFlag(dataFlag);
            productSaleAggregationReq.setStartDate(startDate);
            productSaleAggregationReq.setEndDate(endDate);
            productSaleAggregationReq.setGroupID(Long.valueOf(UserConfig.getGroupID()));
            mPresenter.queryProductSaleAggregation(productSaleAggregationReq);
        }
        //封装查询 商品销售top10
        if(dateSelected==0){
            dataFlag = (byte) 1;
            startDate = DateUtil.getWeekFirstDay(0);
            endDate = DateUtil.getWeekLastDay(0);
        }else if(dateSelected ==1){
            dataFlag = (byte) 2;
            startDate = DateUtil.getMonthFirstDay(0);
            endDate = DateUtil.getMonthLastDay(0);
        }else if(dateSelected ==2){
            dataFlag = (byte) 3;
            startDate = DateUtil.getMonthFirstDay(-1);
            endDate = DateUtil.getMonthLastDay(-1);
        }
        productSaleTopReq.setDateFlag(dataFlag);
        productSaleTopReq.setStartDate(startDate);
        productSaleTopReq.setEndDate(endDate);
        productSaleTopReq.setType(type);
        productSaleTopReq.setGroupID(Long.valueOf(UserConfig.getGroupID()));
        mPresenter.queryProductSaleTop(productSaleTopReq);
    }
}
