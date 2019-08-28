package com.hll_sc_app.bean.report.refund;

public class WaitRefundTotalResp {

    /**
     * 账期付款金额
     */
    private String accountAmount;
    /**
     * 银行卡付款金额
     */
    private String bankCardAmount;
    /**
     * 现金付款金额
     */
    private String cashAmount;

    /**
     * 在线付款金额
     */
    private String onLineAmount;

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
    private String totalRefundAmount;


    public String getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(String accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getBankCardAmount() {
        return bankCardAmount;
    }

    public void setBankCardAmount(String bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getOnLineAmount() {
        return onLineAmount;
    }

    public void setOnLineAmount(String onLineAmount) {
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

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }
}
