package com.hll_sc_app.bean.report;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */
@StringDef({ReportLabel.SALES_STATISTICS, ReportLabel.ORDER_GOODS_DETAILS, ReportLabel.DAILY_SALES_VOLUME,
        ReportLabel.CUSTOMER_SALES, ReportLabel.SIGNING_PERFORMANCE, ReportLabel.SALES_PERFORMANCE,
        ReportLabel.STOCKOUT_DIFFERENCES, ReportLabel.RECEIVE_DIFFERENCE, ReportLabel.STOCKOUT_PRODUCTS_DETAILS,
        ReportLabel.RECEIVE_DIFFERENCE_DETAILS, ReportLabel.STOCKOUT_STATISTICS, ReportLabel.REFUND_REASONS})
@Retention(RetentionPolicy.SOURCE)
public @interface ReportLabel {
    String SALES_STATISTICS = "商品销量统计汇总";
    String ORDER_GOODS_DETAILS = "客户订货明细汇总";
    String DAILY_SALES_VOLUME = "日销售额汇总";
    String CUSTOMER_SALES = "客户销售汇总";
    String SIGNING_PERFORMANCE = "业务员签约绩效";
    String SALES_PERFORMANCE = "业务员销售额绩效";
    String STOCKOUT_DIFFERENCES = "缺货差异汇总";
    String RECEIVE_DIFFERENCE = "收货差异汇总";
    String STOCKOUT_PRODUCTS_DETAILS = "缺货产品明细表";
    String RECEIVE_DIFFERENCE_DETAILS = "收货差异产品明细表";
    String STOCKOUT_STATISTICS = "客户缺货统计表";
    String REFUND_REASONS = "退货原因统计表";
}
