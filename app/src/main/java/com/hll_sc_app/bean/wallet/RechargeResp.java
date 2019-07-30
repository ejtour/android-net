package com.hll_sc_app.bean.wallet;

/**
 * 充值
 */
public class RechargeResp {
    private String payOrderKey;
    private String preRechargeInfo;
    private String settleUnitID;

    public String getPayOrderKey() {
        return payOrderKey;
    }

    public void setPayOrderKey(String payOrderKey) {
        this.payOrderKey = payOrderKey;
    }

    public String getPreRechargeInfo() {
        return preRechargeInfo;
    }

    public void setPreRechargeInfo(String preRechargeInfo) {
        this.preRechargeInfo = preRechargeInfo;
    }

    public String getSettleUnitID() {
        return settleUnitID;
    }

    public void setSettleUnitID(String settleUnitID) {
        this.settleUnitID = settleUnitID;
    }
}
