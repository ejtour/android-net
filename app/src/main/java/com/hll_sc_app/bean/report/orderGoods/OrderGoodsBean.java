package com.hll_sc_app.bean.report.orderGoods;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsBean {
    private double inspectionNum;
    private double inspectionAmount;
    private double orderAmount;
    private double orderNum;
    private String shopName;
    private String shopID;
    private String purchaserName;
    private double skuNum;

    public double getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(double inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public double getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(double inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(double orderNum) {
        this.orderNum = orderNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(double skuNum) {
        this.skuNum = skuNum;
    }
}
