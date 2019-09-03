package com.hll_sc_app.bean.report;

import android.support.annotation.IntDef;

import com.hll_sc_app.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */
@IntDef({ReportIcon.SALES_STATISTICS, ReportIcon.ORDER_GOODS_DETAILS, ReportIcon.DAILY_SALES_VOLUME,
        ReportIcon.CUSTOMER_SALES, ReportIcon.SIGNING_PERFORMANCE, ReportIcon.SALES_PERFORMANCE,
        ReportIcon.STOCKOUT_DIFFERENCES, ReportIcon.RECEIVE_DIFFERENCE, ReportIcon.PRODUCTS_DETAILS,
    ReportIcon.RECEIVE_DIFFERENCE_DETAILS, ReportIcon.STOCKOUT_STATISTICS, ReportIcon.REFUND_REASONS,
    ReportIcon.SALES_MAN_SIGN,ReportIcon.WAIT_REFUND,ReportIcon.REFUNDED,ReportIcon.REFUNDED_CUSTOMER_PRODUCT,
    ReportIcon.PURCHASE_STATISTIC, ReportIcon.PRODUCE_STATISTIC,ReportIcon.REPORT_GROUP_LOSS,ReportIcon.REPORT_SHOP_LOSS,
   ReportIcon.REPORT_WAREHOUSE_PRODUCT,ReportIcon.REPORT_WAREHOUSE_DELIVERY,ReportIcon.REPORT_WAREHOUSE_SERVICE_FEE})
@Retention(RetentionPolicy.SOURCE)
public @interface ReportIcon {
    int SALES_STATISTICS = R.drawable.ic_report_sales_statistics; // 商品销量统计汇总
    int ORDER_GOODS_DETAILS = R.drawable.ic_report_order_goods_details; // 客户订货明细汇总
    int DAILY_SALES_VOLUME = R.drawable.ic_report_sales_volume; // 日销售额汇总
    int CUSTOMER_SALES = R.drawable.ic_report_customer_sales; // 客户销售汇总
    int SIGNING_PERFORMANCE = R.drawable.ic_report_signing_performance;
    int SALES_PERFORMANCE = R.drawable.ic_report_sales_performance;
    int STOCKOUT_DIFFERENCES = R.drawable.ic_report_stockout_difference;
    int RECEIVE_DIFFERENCE = R.drawable.ic_report_receive_difference;
    int PRODUCTS_DETAILS = R.drawable.ic_report_products_details;
    int RECEIVE_DIFFERENCE_DETAILS = R.drawable.ic_report_receive_difference_details;
    int STOCKOUT_STATISTICS = R.drawable.ic_report_stockout_statistics;
    int REFUND_REASONS = R.drawable.ic_board_question_blue;
    int SALES_MAN_SIGN = R.drawable.ic_salesman_sign;
    int WAIT_REFUND = R.drawable.ic_wait_refund;
    int REFUNDED = R.drawable.ic_report_refunded;
    int REFUNDED_CUSTOMER_PRODUCT = R.drawable.ic_report_refunded_customer_product;
    int PURCHASE_STATISTIC = R.drawable.ic_report_purchase_statistic; // 采购汇总统计
    int PRODUCE_STATISTIC = R.drawable.ic_report_produce_statistic; // 生产汇总统计
    int REPORT_GROUP_LOSS = R.drawable.ic_report_group_loss; //集团流失明细
    int REPORT_SHOP_LOSS = R.drawable.ic_report_shop_loss; //门店流失明细
    int REPORT_WAREHOUSE_PRODUCT = R.drawable.ic_report_warehouse_product;//代仓商品缺货明细
    int REPORT_WAREHOUSE_DELIVERY = R.drawable.ic_report_warehouse_delivery;//代仓发货统计
    int REPORT_WAREHOUSE_SERVICE_FEE = R.drawable.ic_report_warehouse_service_fee;//代仓服务费
}
