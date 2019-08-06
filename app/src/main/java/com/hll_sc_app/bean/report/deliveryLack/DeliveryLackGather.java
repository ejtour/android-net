package com.hll_sc_app.bean.report.deliveryLack;

/**
 * 缺货汇总
 */
public class DeliveryLackGather {

    private long date;
    private double deliveryLackAmount;
    private int deliveryLackKindNum;
    private int deliveryLackNum;
    private String deliveryLackRate;
    private int deliveryLackShopNum;
    private int deliveryOrderNum;
    private double deliveryTradeAmount;
    private double oriReserveTotalAmount;


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getDeliveryLackAmount() {
        return deliveryLackAmount;
    }

    public void setDeliveryLackAmount(double deliveryLackAmount) {
        this.deliveryLackAmount = deliveryLackAmount;
    }

    public int getDeliveryLackKindNum() {
        return deliveryLackKindNum;
    }

    public void setDeliveryLackKindNum(int deliveryLackKindNum) {
        this.deliveryLackKindNum = deliveryLackKindNum;
    }

    public int getDeliveryLackNum() {
        return deliveryLackNum;
    }

    public void setDeliveryLackNum(int deliveryLackNum) {
        this.deliveryLackNum = deliveryLackNum;
    }

    public String getDeliveryLackRate() {
        return deliveryLackRate;
    }

    public void setDeliveryLackRate(String deliveryLackRate) {
        this.deliveryLackRate = deliveryLackRate;
    }

    public int getDeliveryLackShopNum() {
        return deliveryLackShopNum;
    }

    public void setDeliveryLackShopNum(int deliveryLackShopNum) {
        this.deliveryLackShopNum = deliveryLackShopNum;
    }

    public int getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(int deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public double getDeliveryTradeAmount() {
        return deliveryTradeAmount;
    }

    public void setDeliveryTradeAmount(double deliveryTradeAmount) {
        this.deliveryTradeAmount = deliveryTradeAmount;
    }

    public double getOriReserveTotalAmount() {
        return oriReserveTotalAmount;
    }

    public void setOriReserveTotalAmount(double oriReserveTotalAmount) {
        this.oriReserveTotalAmount = oriReserveTotalAmount;
    }
}
