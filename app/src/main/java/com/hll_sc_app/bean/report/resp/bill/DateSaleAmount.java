package com.hll_sc_app.bean.report.resp.bill;

public class DateSaleAmount {
    private double averageAmount;
    private double averageShopAmount;
    private String date;
    private int orderCustomerNum;
    private int orderCustomerShopNum;
    private int orderNum;
    private double refundAmount;
    private int refundBillNum;
    private double subtotalAmount;
    private double tradeAmount;
    private int validOrderNum;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderCustomerNum() {
        return orderCustomerNum;
    }

    public void setOrderCustomerNum(int orderCustomerNum) {
        this.orderCustomerNum = orderCustomerNum;
    }

    public int getOrderCustomerShopNum() {
        return orderCustomerShopNum;
    }

    public void setOrderCustomerShopNum(int orderCustomerShopNum) {
        this.orderCustomerShopNum = orderCustomerShopNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(int refundBillNum) {
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

    public int getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(int validOrderNum) {
        this.validOrderNum = validOrderNum;
    }
}
