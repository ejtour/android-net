package com.hll_sc_app.bean.aftersales;

public class NegotiationHistoryReq {
    private String refundBillID;
    private String subBillID;

    public String getRefundBillID() {
        return refundBillID;
    }

    public void setRefundBillID(String refundBillID) {
        this.refundBillID = refundBillID;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }
}
