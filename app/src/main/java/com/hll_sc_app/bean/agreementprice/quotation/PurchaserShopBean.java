package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 采购商门店
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class PurchaserShopBean {
    private String shopCode;
    private String shopArea;
    private String addressGaoDe;
    private String imagePath;
    private String mobile;
    private String shopName;
    private String source;
    private String isActive;
    private String shopAddress;
    private String linkman;
    private String purchaserID;
    private String latGaoDe;
    private String shopAdmin;
    private String lonGaoDe;
    private String shopPhone;
    private String shopID;
    private boolean select;
    private List<TimeBean> time;

    public List<TimeBean> getTime() {
        return time;
    }

    public void setTime(List<TimeBean> time) {
        this.time = time;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

    public String getAddressGaoDe() {
        return addressGaoDe;
    }

    public void setAddressGaoDe(String addressGaoDe) {
        this.addressGaoDe = addressGaoDe;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getLatGaoDe() {
        return latGaoDe;
    }

    public void setLatGaoDe(String latGaoDe) {
        this.latGaoDe = latGaoDe;
    }

    public String getShopAdmin() {
        return shopAdmin;
    }

    public void setShopAdmin(String shopAdmin) {
        this.shopAdmin = shopAdmin;
    }

    public String getLonGaoDe() {
        return lonGaoDe;
    }

    public void setLonGaoDe(String lonGaoDe) {
        this.lonGaoDe = lonGaoDe;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }
}
