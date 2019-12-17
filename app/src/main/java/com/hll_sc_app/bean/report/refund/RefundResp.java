package com.hll_sc_app.bean.report.refund;

public class RefundResp {

    /**
     * 账期付款金额
     */
    private double accountAmount;
    /**
     * 银行卡付款金额
     */
    private double bankCardAmount;
    /**
     * 现金付款金额
     */
    private double cashAmount;

    /**
     * 在线付款金额
     */
    private double onLineAmount;

    /**
     * 退款单数
     */
    private long refundBillNum;

    /**
     * 退款集团客户数
     */
    private long refundGroupCustomerNum;

    /**
     * 退款门店客户数
     */
    private long refundShopCustomerNum;

    /**
     * 退款总金额
     */
    private double totalRefundAmount;

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public double getBankCardAmount() {
        return bankCardAmount;
    }

    public void setBankCardAmount(double bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public double getOnLineAmount() {
        return onLineAmount;
    }

    public void setOnLineAmount(double onLineAmount) {
        this.onLineAmount = onLineAmount;
    }

    public long getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(long refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public long getRefundGroupCustomerNum() {
        return refundGroupCustomerNum;
    }

    public void setRefundGroupCustomerNum(long refundGroupCustomerNum) {
        this.refundGroupCustomerNum = refundGroupCustomerNum;
    }

    public long getRefundShopCustomerNum() {
        return refundShopCustomerNum;
    }

    public void setRefundShopCustomerNum(long refundShopCustomerNum) {
        this.refundShopCustomerNum = refundShopCustomerNum;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }
}
