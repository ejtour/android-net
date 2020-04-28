package com.hll_sc_app.bean.order.summary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class SummaryShopBean {
    private double productAmount;
    private int productCount;
    private double productNum;
    private String shipperID;
    @SerializedName(value = "shopID", alternate = "stallID")
    private String shopID;
    @SerializedName(value = "shopName", alternate = "stallName")
    private String shopName;
    private List<SummaryShopBean> stallList;

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
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

    public List<SummaryShopBean> getStallList() {
        return stallList;
    }

    public void setStallList(List<SummaryShopBean> stallList) {
        this.stallList = stallList;
    }
}
