package com.hll_sc_app.bean.shop;

import java.util.List;

public class SupplierShopUpdateReq {

    private String businessEndTime;
    private String businessStartTime;
    private Integer isActive;
    private String logoUrl;
    private String shopAddress;
    private String shopAdmin;
    private ShopAreaBean shopArea;
    private String shopID;
    private String shopName;
    private String shopPhone;
    private List<CategoryBean> category;

    public String getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(String businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopAdmin() {
        return shopAdmin;
    }

    public void setShopAdmin(String shopAdmin) {
        this.shopAdmin = shopAdmin;
    }

    public ShopAreaBean getShopArea() {
        return shopArea;
    }

    public void setShopArea(ShopAreaBean shopArea) {
        this.shopArea = shopArea;
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

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

}
