package com.hll_sc_app.bean.report.refund;

public class WaitRefundCustomerItem {

    /**
     * 账期金额
     */
    private String accountAmount;
    /**
     * 银行卡金额
     */
    private String bankCardAmount;
    /**
     * 现金金额
     */
    private String cashAmount;
    /**
     * 在线支付金额
     */
    private String onLineAmount;
    /**
     * 采购商集团ID
     */
    private long   purchaserID;
    /**
     * 采购商集团名称
     */
    private String purchaserName;
    /**
     * 退货退款单数
     */
    private String   refundBillNum;
    /**
     * 待退商品数
     */
    private String   refundProductNum;
    /**
     * 门店ID
     */
    private long   shopID;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 小计金额
     */
    private String subRefundAmount;

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

    public long getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(long purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(String refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public String getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(String refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public long getShopID() {
        return shopID;
    }

    public void setShopID(long shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSubRefundAmount() {
        return subRefundAmount;
    }

    public void setSubRefundAmount(String subRefundAmount) {
        this.subRefundAmount = subRefundAmount;
    }
}
