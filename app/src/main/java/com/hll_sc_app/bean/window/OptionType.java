package com.hll_sc_app.bean.window;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */
@StringDef({OptionType.OPTION_EXPORT_ASSEMBLY, OptionType.OPTION_EXPORT_CHECK_CATEGORY,
    OptionType.OPTION_EXPORT_CHECK_DETAILS, OptionType.OPTION_EXPORT_ORDER,
    OptionType.OPTION_EXPORT_ORDER_DETAILS, OptionType.OPTION_EXPORT_OUT_CATEGORY,
    OptionType.OPTION_EXPORT_OUT_DETAILS, OptionType.OPTION_SORT_CREATE,
    OptionType.OPTION_SORT_DELIVER, OptionType.OPTION_FILTER_CREATE, OptionType.OPTION_EXPORT_OUT,
    OptionType.OPTION_FILTER_EXECUTE, OptionType.OPTION_FILTER_SIGN, OptionType.OPTION_GOODS_ADD,
    OptionType.OPTION_GOODS_IMPORT, OptionType.OPTION_GOODS_TOP, OptionType.OPTION_GOODS_RELATION,
    OptionType.OPTION_GOODS_WARN, OptionType.OPTION_GOODS_EXPORT, OptionType.OPTION_EXPORT_RECORD,
    OptionType.OPTION_AGREEMENT_PRICE_EXPORT})
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
    String OPTION_SORT_CREATE = "按下单时间排序";
    String OPTION_SORT_DELIVER = "按送货时间排序";
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
}
