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
    ReportLabel.RECEIVE_DIFFERENCE_DETAILS, ReportLabel.STOCKOUT_STATISTICS, ReportLabel.REFUND_REASONS,
    ReportLabel.SALES_MAN_SIGN_ACHIEVEMENT, ReportLabel.SALES_MAN_SALES_ACHIEVEMENT, ReportLabel.DELIVERY_LACK_GATHER,
    ReportLabel.CUSTOMER_LACK_AGGREGATION,ReportLabel.INSPECT_LACK_AGGREGATION,ReportLabel.INSPECT_LACK_DETAIL,
        ReportLabel.WAREHOUSE_PRODUCT_DETAIL,ReportLabel.DELIVERY_TIME_AGGREGATION,ReportLabel.WAIT_FOR_REFUND,
        ReportLabel.REFUNDED,ReportLabel.REFUNDED_CUSTOMER_PRODUCT})
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
    String SALES_MAN_SIGN_ACHIEVEMENT = "业务员签约绩效";
    String SALES_MAN_SALES_ACHIEVEMENT = "业务员销售额绩效";
    String DELIVERY_LACK_GATHER = "缺货差异汇总";
    String CUSTOMER_LACK_AGGREGATION="客户缺货统计表";
    String INSPECT_LACK_AGGREGATION="收货差异汇总";
    String INSPECT_LACK_DETAIL="收货差异商品明细表";
    String WAREHOUSE_PRODUCT_DETAIL="代仓商品缺货明细";
    String DELIVERY_TIME_AGGREGATION="配送及时率统计";
    String WAIT_FOR_REFUND = "待退货统计表";
    String REFUNDED = "退货统计表";
    String REFUNDED_CUSTOMER_PRODUCT = "退货客户与商品统计表";
}
