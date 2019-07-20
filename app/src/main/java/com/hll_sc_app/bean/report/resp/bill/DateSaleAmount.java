package com.hll_sc_app.bean.report.resp.bill;

public class DateSaleAmount {
    private double averageAmount;
    private double averageShopAmount;
    private long   date;
    private long   orderCustomerNum;
    private int    orderCustomerShopNum;
    private long   orderNum;
    private double refundAmount;
    private long   refundBillNum;
    private double subtotalAmount;
    private double tradeAmount;
    private long   validOrderNum;

    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    public double getAverageShopAmount() {
        return averageShopAmount;
    }

    public void setAverageShopAmount(double averageShopAmount) {
        this.averageShopAmount = averageShopAmount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getOrderCustomerNum() {
        return orderCustomerNum;
    }

    public void setOrderCustomerNum(long orderCustomerNum) {
        this.orderCustomerNum = orderCustomerNum;
    }

    public int getOrderCustomerShopNum() {
        return orderCustomerShopNum;
    }

    public void setOrderCustomerShopNum(int orderCustomerShopNum) {
        this.orderCustomerShopNum = orderCustomerShopNum;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
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
