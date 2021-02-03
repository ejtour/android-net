package com.hll_sc_app.app.aftersales.apply.text;

import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public class AfterSalesRejectText implements IAfterSalesApplyContract.IAfterSalesApplyText {
    @Override
    public String getTitle() {
        return "拒收";
    }

    @Override
    public String getWarmTip() {
        return "温馨提示：" +
                "\n• 如果客户付款时选择的是现金、刷卡等线下支付方式时，商品全部拒收时无需支付金额。" +
                "\n• 如果客户付款时选择账期支付时，退货款项会在结算时自动扣除！" +
                "\n• 供货商完成退款后，线上支付款项会根据订单的支付方式原路返还！";
    }

    @Override
    public String getLabel() {
        return getTitle();
    }

    @Override
    public String getReasonPrefix() {
        return getTitle();
    }

    @Override
    public String getEditHint() {
        return "写拒收说明";
    }

    @Override
    public String getDetailsLabel() {
        return "商品明细";
    }

    @Override
    public String getAddTip() {
        return null;
    }

    @Override
    public String getMoneyLabel() {
        return "收款金额";
    }
}
