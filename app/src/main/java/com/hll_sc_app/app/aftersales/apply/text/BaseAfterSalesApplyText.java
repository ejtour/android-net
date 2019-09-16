package com.hll_sc_app.app.aftersales.apply.text;

import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public abstract class BaseAfterSalesApplyText implements IAfterSalesApplyContract.IAfterSalesApplyText {

    @Override
    public String getWarmTip() {
        return "温馨提示：" +
                "\n• 采购商付款时选择的是现金、刷卡等线下支付方式时，退货款项请联系供应商线下处理！" +
                "\n• 采购商付款时选择账期支付时，退货款项会在结算时自动扣除！" +
                "\n• 采购商付款时选择线上支付时，相关款项会根据订单的支付方式原路返还！";
    }

    @Override
    public String getEditHint() {
        return String.format("请您在此填写%s说明", getLabel());
    }

    @Override
    public String getDetailsLabel() {
        return String.format("%s明细", getLabel());
    }

    @Override
    public String getAddTip() {
        return "点击添加"+getLabel()+"明细";
    }

    @Override
    public String getMoneyLabel() {
        return "退款金额";
    }
}
