package com.hll_sc_app.bean.delivery;


import java.util.List;

/**
 * 起购金额-选择采购商门店
 *
 * @author zhuyingsong
 * @date 2019-07-31
 */
public class ShopMinimumSelectBean {
    private List<String> purchaserShopList;

    public List<String> getPurchaserShopList() {
        return purchaserShopList;
    }

    public void setPurchaserShopList(List<String> purchaserShopList) {
        this.purchaserShopList = purchaserShopList;
    }
}
