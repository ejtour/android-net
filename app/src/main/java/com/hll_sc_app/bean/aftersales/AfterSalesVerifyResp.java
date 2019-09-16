package com.hll_sc_app.bean.aftersales;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesVerifyResp {
    private boolean canRefund;
    private String tips;
    private int subBillType;

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getSubBillType() {
        return subBillType;
    }

    public void setSubBillType(int subBillType) {
        this.subBillType = subBillType;
    }
}
