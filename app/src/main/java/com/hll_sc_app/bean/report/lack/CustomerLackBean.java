package com.hll_sc_app.bean.report.lack;

public class CustomerLackBean {

    private int deliveryLackKindNum;
    private double deliveryLackAmount;
    private int deliveryLackNum;
    private String purchaserID;
    private String purchaserName;
    private String shopID;
    private String shopName;

    public int getDeliveryLackKindNum() {
        return deliveryLackKindNum;
    }

    public void setDeliveryLackKindNum(int deliveryLackKindNum) {
        this.deliveryLackKindNum = deliveryLackKindNum;
    }

    public double getDeliveryLackAmount() {
        return deliveryLackAmount;
    }

    public void setDeliveryLackAmount(double deliveryLackAmount) {
        this.deliveryLackAmount = deliveryLackAmount;
    }

    public int getDeliveryLackNum() {
        return deliveryLackNum;
    }

    public void setDeliveryLackNum(int deliveryLackNum) {
        this.deliveryLackNum = deliveryLackNum;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
