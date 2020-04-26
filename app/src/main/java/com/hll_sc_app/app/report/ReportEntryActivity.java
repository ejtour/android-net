package com.hll_sc_app.app.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.ReportItem;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterConfig.REPORT_ENTRY)
public class ReportEntryActivity extends BaseLoadActivity {

    @BindView(R.id.are_list_view)
    RecyclerView mListView;
    @BindView(R.id.are_title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "object0")
    String mTitle;

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.REPORT_ENTRY);
    }

    public static void start(String title) {
        RouterUtil.goToActivity(RouterConfig.REPORT_ENTRY, title);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_entry);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mListView.setAdapter(new ReportEntryAdapter(prepareMenu()));
    }

    private List<ReportItem> prepareMenu() {
        List<ReportItem> list = new ArrayList<>();
        if (!TextUtils.isEmpty(mTitle)) {
            mTitleBar.setHeaderTitle(mTitle);
            list.add(new ReportItem(R.drawable.ic_report_customer_receive, "客户收货查询", RouterConfig.REPORT_CUSTOMER_RECEIVE));
            list.add(new ReportItem(R.drawable.ic_report_customer_settle, "客户结算查询", RouterConfig.REPORT_CUSTOMER_SETTLE));
            list.add(new ReportItem(R.drawable.ic_report_credit_customer, "对账单", RouterConfig.BILL_LIST));
        } else if (UserConfig.crm()) {
            list.add(new ReportItem(R.drawable.ic_salesman_sign, "业务员签约绩效", RouterConfig.REPORT_SALESMAN_SIGN));
            list.add(new ReportItem(R.drawable.ic_report_sales_performance, "业务员销售额绩效", RouterConfig.REPORT_SALESMAN_SALES, true));

            list.add(new ReportItem(R.drawable.ic_report_group_loss, "客户流失率统计", RouterConfig.REPORT_CUSTOMER_LOSS));
            list.add(new ReportItem(R.drawable.ic_report_shop_loss, "流失门店明细表", RouterConfig.REPORT_SHOP_LOSS, true));

            list.add(new ReportItem(R.drawable.ic_report_group_loss, "日报统计", RouterConfig.REPORT_SALES_DAILY, true));

            list.add(new ReportItem(R.drawable.ic_report_shop_sales, "门店销售汇总表", RouterConfig.CUSTOMER_SALE_AGGREGATION_DETAIL, true));
        } else {
            list.add(new ReportItem(R.drawable.ic_report_sales_statistics, "商品销量统计汇总", RouterConfig.REPORT_PRODUCT_SALES_STATISTICS));
            list.add(new ReportItem(R.drawable.ic_report_order_goods_details, "客户订货统计", RouterConfig.REPORT_ORDER_GOODS, true));

            list.add(new ReportItem(R.drawable.ic_report_sales_volume, "日销售额汇总", RouterConfig.REPORT_DAILY_AGGREGATION));
            list.add(new ReportItem(R.drawable.ic_report_customer_sales, "客户销售汇总", RouterConfig.CUSTOMER_SALE_AGGREGATION, true));

            list.add(new ReportItem(R.drawable.ic_salesman_sign, "业务员签约绩效", RouterConfig.REPORT_SALESMAN_SIGN));
            list.add(new ReportItem(R.drawable.ic_report_sales_performance, "业务员销售额绩效", RouterConfig.REPORT_SALESMAN_SALES, true));

            list.add(new ReportItem(R.drawable.ic_report_stockout_difference, "缺货差异汇总", RouterConfig.REPORT_LACK_DIFF));
            list.add(new ReportItem(R.drawable.ic_report_receive_difference, "收货差异汇总", RouterConfig.REPORT_RECEIVE_DIFF));
            list.add(new ReportItem(R.drawable.ic_report_products_details, "缺货商品明细表", RouterConfig.REPORT_LACK_DETAILS));
            list.add(new ReportItem(R.drawable.ic_report_products_details, "收货差异商品明细表", RouterConfig.REPORT_RECEIVE_DIFF_DETAILS));
            list.add(new ReportItem(R.drawable.ic_report_stockout_statistics, "客户缺货统计表", RouterConfig.REPORT_CUSTOMER_LACK, true));

            list.add(new ReportItem(R.drawable.ic_report_receive_difference_details, "配送及时率统计", RouterConfig.REPORT_DELIVERY_TIME, true));

            list.add(new ReportItem(R.drawable.ic_wait_refund, "待退货统计表", RouterConfig.REPORT_WAIT_REFUND));
            list.add(new ReportItem(R.drawable.ic_report_refund, "退货统计表", RouterConfig.REPORT_REFUND_STATISTIC));
            list.add(new ReportItem(R.drawable.ic_report_refund_customer_product, "退货客户与商品统计表", RouterConfig.REPORT_REFUND_CUSTOMER_PRODUCT, true));

            list.add(new ReportItem(R.drawable.ic_report_customer_profit, "客户毛利统计表", RouterConfig.REPORT_PROFIT_CUSTOMER));
            list.add(new ReportItem(R.drawable.ic_report_shop_sales, "门店毛利统计表", RouterConfig.REPORT_PROFIT_SHOP));
            list.add(new ReportItem(R.drawable.ic_report_category_profit, "品类毛利统计表", RouterConfig.REPORT_PROFIT_CATEGORY, true));

            list.add(new ReportItem(R.drawable.ic_report_credit_customer, "客户应收账款", RouterConfig.REPORT_CREDIT));
            list.add(new ReportItem(R.drawable.ic_report_credit_details, "客户应收账款明细表", RouterConfig.REPORT_CREDIT_DETAILS_CUSTOMER));
            list.add(new ReportItem(R.drawable.ic_report_credit_daily, "日应收账款汇总表", RouterConfig.REPORT_CREDIT_DETAILS_DAILY, true));

            list.add(new ReportItem(R.drawable.ic_report_group_loss, "客户流失率统计", RouterConfig.REPORT_CUSTOMER_LOSS));
            list.add(new ReportItem(R.drawable.ic_report_shop_loss, "流失门店明细表", RouterConfig.REPORT_SHOP_LOSS, true));

            list.add(new ReportItem(R.drawable.ic_report_purchase_statistic, "采购汇总统计", RouterConfig.REPORT_PURCHASE_STATISTIC));
            list.add(new ReportItem(R.drawable.ic_report_produce_statistic, "生产汇总统计", RouterConfig.REPORT_PRODUCE_STATISTIC, true));

            if (!BuildConfig.isOdm) {
                list.add(new ReportItem(R.drawable.ic_report_warehouse_product, "代仓商品缺货明细", RouterConfig.REPORT_WAREHOUSE_PRODUCT_DETAIL));
                list.add(new ReportItem(R.drawable.ic_report_warehouse_delivery, "代仓发货统计", RouterConfig.REPORT_WAREHOUSE_DELIVERY));
                list.add(new ReportItem(R.drawable.ic_report_warehouse_service_fee, "代仓服务费统计", RouterConfig.REPORT_WAREHOUSE_FEE, true));
            }

            list.add(new ReportItem(R.drawable.ic_report_group_loss, "日报统计", RouterConfig.REPORT_SALES_DAILY, true));

            list.add(new ReportItem(R.drawable.ic_board_question_blue, "退货原因统计", RouterConfig.REFUND_REASON_STATICS, true));

            list.add(new ReportItem(R.drawable.ic_query_custom_receive, "客户收货查询", RouterConfig.REPORT_CUSTOMER_RECEIVE, true));
        }
        return list;
    }
}
