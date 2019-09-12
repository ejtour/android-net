package com.hll_sc_app.app.aftersales.apply.text;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public class AfterSalesGoodsText extends BaseAfterSalesApplyText {
    @Override
    public String getTitle() {
        return "退货退款";
    }

    @Override
    public String getWarmTip() {
        return "温馨提示：" +
                "\n• 如果您付款时选择的是现金、刷卡等线下支付方式时，退货款项请联系供应商线下处理！" +
                "\n• 如果您付款时选择账期支付时，退货款项会在结算时自动扣除！" +
                "\n• 供货商完成退款后，相关款项会根据订单的支付方式原路返还！";
    }

    @Override
    public String getLabel() {
        return "退货";
    }

    @Override
    public String getReasonPrefix() {
        return getTitle();
    }

    @Override
    public String getMoneyLabel() {
        return "退款金额";
    }
}
