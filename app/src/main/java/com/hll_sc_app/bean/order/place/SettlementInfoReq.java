package com.hll_sc_app.bean.order.place;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SettlementInfoReq {
    private String purchaserID;
    private String purchaserName;
    private String shopID;
    private String shopName;
    private List<PlaceOrderSpecBean> specs;

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

    public List<PlaceOrderSpecBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<PlaceOrderSpecBean> specs) {
        this.specs = specs;
    }
}
