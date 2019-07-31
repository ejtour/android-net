package com.hll_sc_app.bean.window;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */
@StringDef({OptionType.OPTION_EXPORT_ASSEMBLY, OptionType.OPTION_EXPORT_CHECK_CATEGORY,
        OptionType.OPTION_EXPORT_CHECK_DETAILS, OptionType.OPTION_EXPORT_ORDER, OptionType.OPTION_EXPORT_DETAILS,
        OptionType.OPTION_EXPORT_ORDER_DETAILS, OptionType.OPTION_EXPORT_OUT_CATEGORY,
        OptionType.OPTION_EXPORT_OUT_DETAILS, OptionType.OPTION_FILTER_CREATE, OptionType.OPTION_EXPORT_OUT,
        OptionType.OPTION_FILTER_EXECUTE, OptionType.OPTION_FILTER_SIGN, OptionType.OPTION_GOODS_ADD,
        OptionType.OPTION_GOODS_IMPORT, OptionType.OPTION_GOODS_TOP, OptionType.OPTION_GOODS_RELATION,
        OptionType.OPTION_GOODS_WARN, OptionType.OPTION_GOODS_EXPORT, OptionType.OPTION_EXPORT_RECORD,
        OptionType.OPTION_AGREEMENT_PRICE_EXPORT, OptionType.OPTION_COOPERATION_ADD,
        OptionType.OPTION_COOPERATION_RECEIVE, OptionType.OPTION_COOPERATION_SEND, OptionType.OPTION_COOPERATION_EXPORT,
        OptionType.OPTION_COOPERATION_DETAIL_SETTLEMENT, OptionType.OPTION_COOPERATION_DETAIL_SALESMAN,
        OptionType.OPTION_COOPERATION_DETAIL_DRIVER, OptionType.OPTION_COOPERATION_DETAIL_DELIVERY,
        OptionType.OPTION_ALL, OptionType.OPTION_NOT_CONTAIN_DEPOSIT,
        OptionType.OPTION_COOPERATION_DETAIL_SHOP, OptionType.OPTION_EXPORT_DETAIL_INFO, OptionType.OPTION_REPORT_CURRENT_DATE,
        OptionType.OPTION_REPORT_YES_DATE, OptionType.OPTION_REPORT_CURRENT_WEEK, OptionType.OPTION_REPORT_PRE_WEEK,
        OptionType.OPTION_REPORT_CURRENT_MONTH, OptionType.OPTION_REPORT_PRE_MONTH, OptionType.OPTION_REPORT_CUSTOMER_DEFINE,
        OptionType.OPTION_REPORT_DETAIL, OptionType.OPTION_AGREEMENT_PRICE_LOG_EXPORT})
@Retention(RetentionPolicy.SOURCE)
public @interface OptionType {
    String OPTION_EXPORT_ASSEMBLY = "导出配货单";
    String OPTION_EXPORT_OUT = "导出出货单";
    String OPTION_EXPORT_OUT_DETAILS = "导出明细出库单";
    String OPTION_EXPORT_OUT_CATEGORY = "导出分类出库单";
    String OPTION_EXPORT_ORDER = "导出订单";
    String OPTION_EXPORT_ORDER_DETAILS = "导出订单明细";
    String OPTION_EXPORT_CHECK_DETAILS = "导出明细验货单";
    String OPTION_EXPORT_CHECK_CATEGORY = "导出分类验货单";
    String OPTION_FILTER_CREATE = "按下单时间筛选";
    String OPTION_FILTER_EXECUTE = "按到货时间筛选";
    String OPTION_FILTER_SIGN = "按签收时间筛选";

    String OPTION_GOODS_ADD = "新增商品";
    String OPTION_GOODS_IMPORT = "从商品库导入";
    String OPTION_GOODS_EXPORT = "导出商品列表";
    String OPTION_GOODS_TOP = "商品置顶管理";
    String OPTION_GOODS_RELATION = "第三方商品关联";
    String OPTION_GOODS_WARN = "代仓商品库存预警";
    String OPTION_EXPORT_RECORD = "上下架记录导出";

    String OPTION_AGREEMENT_PRICE_EXPORT = "导出协议价";
    String OPTION_AGREEMENT_PRICE_LOG_EXPORT = "导出变更日志";

    String OPTION_COOPERATION_ADD = "新增合作采购商";
    String OPTION_COOPERATION_RECEIVE = "我收到的申请";
    String OPTION_COOPERATION_SEND = "我发出的申请";
    String OPTION_COOPERATION_EXPORT = "导出合作采购商";

    String OPTION_COOPERATION_DETAIL_SETTLEMENT = "批量修改结算方式";
    String OPTION_COOPERATION_DETAIL_SALESMAN = "批量指派销售";
    String OPTION_COOPERATION_DETAIL_DRIVER = "批量指派司机";
    String OPTION_COOPERATION_DETAIL_DELIVERY = "批量修改配送方式";
    String OPTION_COOPERATION_DETAIL_SHOP = "新增合作门店";

    String OPTION_EXPORT_DETAILS = "导出明细";

    String OPTION_EXPORT_DETAIL_INFO = "导出详细信息";

    String OPTION_REPORT_CURRENT_DATE = "今日";
    String OPTION_REPORT_YES_DATE = "昨日";
    String OPTION_REPORT_CURRENT_WEEK = "本周";
    String OPTION_REPORT_PRE_WEEK = "上周";
    String OPTION_REPORT_CURRENT_MONTH = "本月";
    String OPTION_REPORT_PRE_MONTH = "上月";
    String OPTION_REPORT_CUSTOMER_DEFINE = "自定义";

    String OPTION_ALL = "全部";
    String OPTION_NOT_CONTAIN_DEPOSIT = "不含押金商品";
    String OPTION_REPORT_DETAIL = "导出详细信息";

}
