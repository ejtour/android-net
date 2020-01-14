package com.hll_sc_app.bean.order.summary;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class SummaryPurchaserBean {
    private String purchaserID;
    private String purchaserLogo;
    private String purchaserName;
    private int shopCount;
    private List<SummaryShopBean> shopList;
    private double totalAmount;

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserLogo() {
        return purchaserLogo;
    }

    public void setPurchaserLogo(String purchaserLogo) {
        this.purchaserLogo = purchaserLogo;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public List<SummaryShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<SummaryShopBean> shopList) {
        this.shopList = shopList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
