package com.hll_sc_app.bean.window;

import com.hll_sc_app.BuildConfig;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */
public interface OptionType {
    String OPTION_EXPORT_ASSEMBLY = "导出配货单";
    String OPTION_EXPORT_OUT = "导出出货单";
    String OPTION_EXPORT_OUT_DETAILS = "导出明细出库单";
    String OPTION_EXPORT_OUT_CATEGORY = "导出分类出库单";
    String OPTION_EXPORT_ORDER = "导出订单";
    String OPTION_EXPORT_ORDER_DETAILS = "导出订单明细";
    String OPTION_EXPORT_CHECK_DETAILS = "导出明细验货单";
    String OPTION_EXPORT_CHECK_CATEGORY = "导出分类验货单";
    String OPTION_FILTER_CREATE = "按下单时间筛选";
    String OPTION_FILTER_EXECUTE = "按要求到货时间筛选";
    String OPTION_FILTER_SIGN = "按签收时间筛选";
    String OPTION_RECEIVE_SUMMARY = "待接单订单汇总";
    String OPTION_DELIVER_SUMMARY = "待发货订单汇总";
    String OPTION_DELIVER_TOTAL = "待发货商品总量";
    String OPTION_DELIVERED_TOTAL = "本月已发货商品总量";

    String OPTION_GOODS_ADD = "新增商品";
    String OPTION_GOODS_ADD_BUNDLE = "新增组合商品";
    String OPTION_GOODS_IMPORT = "从商品库导入";
    String OPTION_GOODS_EXPORT = "导出商品列表";
    String OPTION_GOODS_TOP = "商品置顶管理";
    String OPTION_GOODS_RELATION = "第三方商品关联";
    String OPTION_GOODS_WARN = "代仓商品库存预警";
    String OPTION_EXPORT_RECORD = "上下架记录导出";

    String OPTION_AGREEMENT_PRICE_EXPORT = "导出协议价";
    String OPTION_AGREEMENT_PRICE_LOG_EXPORT = "导出变更日志";

    String OPTION_COOPERATION_ADD = "新增合作客户";
    String OPTION_WAREHOUSE_ADD = "新签代仓公司";
    String OPTION_WAREHOUSE_CLIENT = "新签代仓客户";
    String OPTION_COOPERATION_RECEIVE = "我收到的申请";
    String OPTION_COOPERATION_SEND = "我发出的申请";
    String OPTION_COOPERATION_EXPORT = "导出合作客户";

    String OPTION_COOPERATION_DETAIL_SETTLEMENT = "批量修改结算方式";
    String OPTION_COOPERATION_DETAIL_SALESMAN = "批量指派销售";
    String OPTION_COOPERATION_DETAIL_DRIVER = "批量指派司机";
    String OPTION_COOPERATION_DETAIL_DELIVERY = "批量修改配送方式";
    String OPTION_COOPERATION_DETAIL_SHOP = "新增合作门店";

    String OPTION_EXPORT_DETAILS = "导出明细";

    String OPTION_EXPORT_DETAIL_INFO = "导出详细信息";

    String OPTION_REPORT_YES_DATE = "昨日";
    String OPTION_REPORT_PRE_WEEK = "上周";
    String OPTION_REPORT_PRE_MONTH = "上月";
    String OPTION_REPORT_CUSTOMER_DEFINE = "自定义";

    String OPTION_CURRENT_DATE = "今日";
    String OPTION_CURRENT_WEEK = "本周";
    String OPTION_CURRENT_MONTH = "本月";

    String OPTION_ALL = "全部";
    String OPTION_NOT_CONTAIN_DEPOSIT = "不含押金商品";

    String OPTION_EXPORT_BILL = "导出对账单";
    String OPTION_EXPORT_BILL_DETAIL = "导出明细对账单";
    String OPTION_BATCH_SETTLEMENT = "批量结算";

    String OPTION_EXPORT_INVOICE = "导出开票记录";

    String OPTION_REPORT_PRE_SEVEN_DATE = "近7日";
    String OPTION_REPORT_PRE_THIRTY_DATE = "近30日";
    String OPTION_REPORT_PRE_NINETY_DATE = "近90日";

    String OPTION_NOT_DEPOSIT = "不包含押金商品";


    String OPTION_EXPORT_PRICE_MANAGE_LOG = "导出售价记录";
    String OPTION_CHECK_PRICE_MANAGE_CHANGE_LOG = "查看变价日志";

    String OPTION_EXPORT_SUMMARY_TABLE = "导出汇总表";
    String OPTION_RECORD_PURCHASE_LOGISTICS = "录入采购物流";
    String OPTION_RECORD_PURCHASE_AMOUNT = "录入采购金额";

    String OPTION_PRE_SEVEN_LOSS = "7日流失";
    String OPTION_PRE_THIRTY_LOSS = "30日流失";

    String OPTION_EXPORT_DETAILS_TABLE = "导出明细表";
    String OPTION_SET_MAN_HOUR_COST = "设置工时费";
    String OPTION_RECORD_PRODUCE_DATA = "新增生产数据";

    String OPTION_PURCHASER_ORDER_CREATE_DATE="采购日期";
    String OPTION_PURCHASER_ORDER_ARRIVAL_DATE="到货日期";
    String OPTION_STOCK_LOG_EXPORT = "导出库存日志";

    String OPTION_COMPLAIN_ADD = "新增投诉记录";
    String OPTION_COMPLAIN_EXPORT = "导出投诉记录";

    String OPTION_PHONE_LINK = "电话联系";

    String OPTION_STATISTIC_DATE = "日统计";
    String OPTION_STATISTIC_WEEK = "周统计";
    String OPTION_STATISTIC_MONTH = "月统计";
    String OPTION_STATISTIC_YEAR = "年统计";

    String OPTION_HAND_OVER_OTHERS = "批量转交其他人";
    String OPTION_HAND_OVER_SEA = "批量转到公海";

    String OPTION_COOPER_PURCHASER = "合作客户";
    String OPTION_STOP_COOPER_PURCHASER = "停止合作客户";

    String OPTION_WAREHOUSE_COMPANY = "我是代仓公司";
    String OPTION_STOP_WAREHOUSE_COMPANY = "停止代仓公司";

    String OPTION_WEEK = "周";
    String OPTION_MONTH = "月";

    String OPTION_REPORT_EXPORT_CUSTOMER_RECEIVE_LIST = "导出单据表";
    String OPTION_REPORT_EXPORT_CUSTOMER_RECEIVE_DETAIL = "导出明细表";

    String OPTION_EXPORT_PURCHASE_TEMPLATE = "导出采购模板";
    String OPTION_EXPORT_ORDER_DETAIL = "订单分享";

    String OPTION_ORDER_SHOP = "下单门店";
    String OPTION_NOT_ORDER_SHOP = "未下单门店";

    String OPTION_SELECT_PURCHASER = "选择合作客户";
    String OPTION_SELECT_CUSTOMER = "选择意向客户";

    String OPTION_EXPORT_PEND_DELIVERY_GOODS = "导出待发货商品";
    String OPTION_EXPORT_DELIVERED_GOODS = "导出已发货商品";
    String OPTION_EXPORT_PEND_RECEIVE_GOODS = "导出待接单商品";

    String OPTION_CUSTOMER_REGISTERED = "已注册" + BuildConfig.ODM_NAME + "客户";
    String OPTION_CUSTOMER_UNREGISTERED = "未注册" + BuildConfig.ODM_NAME + "客户";

    String OPTION_EXPORT_VOUCHER = "导出单据";
    String OPTION_EXPORT_VOUCHER_DETAIL = "导出单据详情";

    String OPTION_EXPORT_STOCK_INFO = "导出库存信息";
}
