package com.hll_sc_app.bean.cooperation;

import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

public class CooperationShopListReq {
    private List<PurchaserShopBean> shopList;

    public List<PurchaserShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<PurchaserShopBean> shopList) {
        this.shopList = shopList;
    }
}
