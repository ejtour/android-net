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
    public String getLabel() {
        return "退回";
    }

    @Override
    public String getReasonPrefix() {
        return getLabel();
    }
}
