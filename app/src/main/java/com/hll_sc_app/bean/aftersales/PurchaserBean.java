package com.hll_sc_app.bean.aftersales;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserBean {
    private String odmId;
    private String purchaserID;
    private String purchaserName;
    private List<PurchaserShopBean> shopList;

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
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

    public List<PurchaserShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<PurchaserShopBean> shopList) {
        this.shopList = shopList;
    }
}
