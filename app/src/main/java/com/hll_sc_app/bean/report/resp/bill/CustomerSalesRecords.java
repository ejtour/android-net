package com.hll_sc_app.bean.report.resp.bill;

public class CustomerSalesRecords {

    private double averageAmount;
    private int cooperationShopNum;
    private long orderNum;
    private long purchaserID;
    private String purchaserName;
    private double refundAmount;
    private long refundBillNum;
    private String shopName;
    private double subtotalAmount;
    private double tradeAmount;
    private long   validOrderNum;


    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    public int getCooperationShopNum() {
        return cooperationShopNum;
    }

    public void setCooperationShopNum(int cooperationShopNum) {
        this.cooperationShopNum = cooperationShopNum;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
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

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public long getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(long refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public long getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(long validOrderNum) {
        this.validOrderNum = validOrderNum;
    }
}
