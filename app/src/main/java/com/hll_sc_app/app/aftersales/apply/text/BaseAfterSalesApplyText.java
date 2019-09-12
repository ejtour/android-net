package com.hll_sc_app.app.aftersales.apply.text;

import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public abstract class BaseAfterSalesApplyText implements IAfterSalesApplyContract.IAfterSalesApplyText {

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
}
