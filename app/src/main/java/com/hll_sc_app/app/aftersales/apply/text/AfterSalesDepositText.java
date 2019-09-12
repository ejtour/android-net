package com.hll_sc_app.app.aftersales.apply.text;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public class AfterSalesDepositText extends BaseAfterSalesApplyText {

    @Override
    public String getTitle() {
        return "退押金";
    }

    @Override
    public String getWarmTip() {
        return "温馨提示：" +
                "\n• 当商品出现质量问题（非人为）可以进行换货" +
                "\n• 填写换货信息发起换货申请，需等待供应商进行审核" +
                "\n• 审核通过后，等待司机送货";
    }

    @Override
    public String getLabel() {
        return "退回";
    }

    @Override
    public String getReasonPrefix() {
        return getLabel();
    }

    @Override
    public String getMoneyLabel() {
        return null;
    }
}
