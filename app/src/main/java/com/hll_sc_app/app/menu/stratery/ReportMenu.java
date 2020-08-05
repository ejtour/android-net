package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class ReportMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "报表中心";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        if (UserConfig.crm()) {
            list.add(new MenuBean(R.drawable.ic_salesman_sign, "业务员签约绩效", RouterConfig.REPORT_SALESMAN_SIGN));
            list.add(new MenuBean(R.drawable.ic_report_sales_performance, "业务员销售额绩效", RouterConfig.REPORT_SALESMAN_SALES, true));

            list.add(new MenuBean(R.drawable.ic_report_group_loss, "客户流失率统计", RouterConfig.REPORT_CUSTOMER_LOSS));
            list.add(new MenuBean(R.drawable.ic_report_shop_loss, "流失门店明细表", RouterConfig.REPORT_SHOP_LOSS, true));

            list.add(new MenuBean(R.drawable.ic_report_group_loss, "日报统计", RouterConfig.REPORT_SALES_DAILY, true));

            list.add(new MenuBean(R.drawable.ic_report_shop_sales, "门店销售汇总表", RouterConfig.CUSTOMER_SALE_AGGREGATION_DETAIL));
        } else {
            list.add(new MenuBean(R.drawable.ic_report_sales_statistics, "商品销量统计汇总", RouterConfig.REPORT_PRODUCT_SALES_STATISTICS));
            list.add(new MenuBean(R.drawable.ic_report_order_goods_details, "客户订货统计", RouterConfig.REPORT_ORDER_GOODS, true));

            list.add(new MenuBean(R.drawable.ic_report_sales_volume, "日销售额汇总", RouterConfig.REPORT_DAILY_AGGREGATION));
            list.add(new MenuBean(R.drawable.ic_report_customer_sales, "客户销售汇总", RouterConfig.CUSTOMER_SALE_AGGREGATION, true));

            list.add(new MenuBean(R.drawable.ic_salesman_sign, "业务员签约绩效", RouterConfig.REPORT_SALESMAN_SIGN));
            list.add(new MenuBean(R.drawable.ic_report_sales_performance, "业务员销售额绩效", RouterConfig.REPORT_SALESMAN_SALES, true));

            list.add(new MenuBean(R.drawable.ic_report_stockout_difference, "缺货差异汇总", RouterConfig.REPORT_LACK_DIFF));
            list.add(new MenuBean(R.drawable.ic_report_receive_difference, "收货差异汇总", RouterConfig.REPORT_RECEIVE_DIFF));
            list.add(new MenuBean(R.drawable.ic_report_products_details, "缺货商品明细表", RouterConfig.REPORT_LACK_DETAILS));
            list.add(new MenuBean(R.drawable.ic_report_products_details, "收货差异商品明细表", RouterConfig.REPORT_RECEIVE_DIFF_DETAILS));
            list.add(new MenuBean(R.drawable.ic_report_stockout_statistics, "客户缺货统计表", RouterConfig.REPORT_CUSTOMER_LACK, true));

            list.add(new MenuBean(R.drawable.ic_report_receive_difference_details, "配送及时率统计", RouterConfig.REPORT_DELIVERY_TIME, true));

            list.add(new MenuBean(R.drawable.ic_wait_refund, "待退货统计表", RouterConfig.REPORT_WAIT_REFUND));
            list.add(new MenuBean(R.drawable.ic_report_refund, "退货统计表", RouterConfig.REPORT_REFUND_STATISTIC));
            list.add(new MenuBean(R.drawable.ic_report_refund_customer_product, "退货客户与商品统计表", RouterConfig.REPORT_REFUND_CUSTOMER_PRODUCT, true));

            list.add(new MenuBean(R.drawable.ic_report_customer_profit, "客户毛利统计表", RouterConfig.REPORT_PROFIT_CUSTOMER));
            list.add(new MenuBean(R.drawable.ic_report_shop_sales, "门店毛利统计表", RouterConfig.REPORT_PROFIT_SHOP));
            list.add(new MenuBean(R.drawable.ic_report_category_profit, "品类毛利统计表", RouterConfig.REPORT_PROFIT_CATEGORY, true));

            list.add(new MenuBean(R.drawable.ic_report_credit_customer, "客户应收账款", RouterConfig.REPORT_CREDIT));
            list.add(new MenuBean(R.drawable.ic_report_credit_details, "客户应收账款明细表", RouterConfig.REPORT_CREDIT_DETAILS_CUSTOMER));
            list.add(new MenuBean(R.drawable.ic_report_credit_daily, "日应收账款汇总表", RouterConfig.REPORT_CREDIT_DETAILS_DAILY, true));

            list.add(new MenuBean(R.drawable.ic_report_group_loss, "客户流失率统计", RouterConfig.REPORT_CUSTOMER_LOSS));
            list.add(new MenuBean(R.drawable.ic_report_shop_loss, "流失门店明细表", RouterConfig.REPORT_SHOP_LOSS, true));

            list.add(new MenuBean(R.drawable.ic_report_purchase_statistic, "采购汇总统计", RouterConfig.REPORT_PURCHASE_STATISTIC));
            list.add(new MenuBean(R.drawable.ic_report_produce_statistic, "生产汇总统计", RouterConfig.REPORT_PRODUCE_STATISTIC, true));

            if (!BuildConfig.isOdm) {
                list.add(new MenuBean(R.drawable.ic_report_warehouse_product, "代仓商品缺货明细", RouterConfig.REPORT_WAREHOUSE_PRODUCT_DETAIL));
                list.add(new MenuBean(R.drawable.ic_report_warehouse_delivery, "代仓发货统计", RouterConfig.REPORT_WAREHOUSE_DELIVERY));
                list.add(new MenuBean(R.drawable.ic_report_warehouse_service_fee, "代仓服务费统计", RouterConfig.REPORT_WAREHOUSE_FEE, true));
            }

            list.add(new MenuBean(R.drawable.ic_report_group_loss, "日报统计", RouterConfig.REPORT_SALES_DAILY, true));

            list.add(new MenuBean(R.drawable.ic_board_question_blue, "退货原因统计", RouterConfig.REFUND_REASON_STATICS, true));

            list.add(new MenuBean(R.drawable.ic_query_custom_receive, "客户收货查询", RouterConfig.REPORT_CUSTOMER_RECEIVE, true));

            list.add(new MenuBean(R.drawable.ic_report_marketing, "营销活动统计表", RouterConfig.REPORT_MARKETING));
        }
        return list;
    }
}
