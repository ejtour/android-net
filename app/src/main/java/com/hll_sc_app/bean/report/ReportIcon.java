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
    ReportIcon.SALES_MAN_SIGN,ReportIcon.WAIT_REFUND,ReportIcon.REFUNDED,ReportIcon.REFUNDED_CUSTOMER_PRODUCT})
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
}
