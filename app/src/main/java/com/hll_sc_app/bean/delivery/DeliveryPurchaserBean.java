package com.hll_sc_app.bean.delivery;

import java.util.List;

/**
 * 起送金额-采购商
 *
 * @author zhuyingsong
 * @date 2019-07-31
 */
public class DeliveryPurchaserBean {
    private String purchaserID;
    private String purchaserName;
    private int purchaserShopNum;
    private List<String> purchaserShopList;

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

    public int getPurchaserShopNum() {
        return purchaserShopNum;
    }

    public void setPurchaserShopNum(int purchaserShopNum) {
        this.purchaserShopNum = purchaserShopNum;
    }

    public List<String> getPurchaserShopList() {
        return purchaserShopList;
    }

    public void setPurchaserShopList(List<String> purchaserShopList) {
        this.purchaserShopList = purchaserShopList;
    }
}
