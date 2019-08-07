package com.hll_sc_app.bean.bill;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillDetailsBean {
    private double billTotalAmount;
    private String subBillID;
    private String subBillDate;
    private String subBillNo;

    private String refundBillNo;
    private double refundTotalAmount;
    private String refundDate;
    private String refundBillID;

    public String getRefundBillNo() {
        return refundBillNo;
    }

    public void setRefundBillNo(String refundBillNo) {
        this.refundBillNo = refundBillNo;
    }

    public double getRefundTotalAmount() {
        return refundTotalAmount;
    }

    public void setRefundTotalAmount(double refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundBillID() {
        return refundBillID;
    }

    public void setRefundBillID(String refundBillID) {
        this.refundBillID = refundBillID;
    }

    public double getBillTotalAmount() {
        return billTotalAmount;
    }

    public void setBillTotalAmount(double billTotalAmount) {
        this.billTotalAmount = billTotalAmount;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public String getSubBillDate() {
        return subBillDate;
    }

    public void setSubBillDate(String subBillDate) {
        this.subBillDate = subBillDate;
    }

    public String getSubBillNo() {
        return subBillNo;
    }

    public void setSubBillNo(String subBillNo) {
        this.subBillNo = subBillNo;
    }
}
