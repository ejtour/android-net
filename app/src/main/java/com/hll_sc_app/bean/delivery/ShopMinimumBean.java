package com.hll_sc_app.bean.delivery;


import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * 起购金额-选择采购商门店
 *
 * @author zhuyingsong
 * @date 2019-07-31
 */
public class ShopMinimumBean {
    private String shopProvince;
    private List<PurchaserShopBean> purchaserShops;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getShopProvince() {
        return shopProvince;
    }

    public void setShopProvince(String shopProvince) {
        this.shopProvince = shopProvince;
    }

    public List<PurchaserShopBean> getPurchaserShops() {
        return purchaserShops;
    }

    public void setPurchaserShops(List<PurchaserShopBean> purchaserShops) {
        this.purchaserShops = purchaserShops;
    }
}
