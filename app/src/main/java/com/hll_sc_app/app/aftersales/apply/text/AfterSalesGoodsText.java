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
    public String getLabel() {
        return "退货";
    }

    @Override
    public String getReasonPrefix() {
        return getTitle();
    }
}
