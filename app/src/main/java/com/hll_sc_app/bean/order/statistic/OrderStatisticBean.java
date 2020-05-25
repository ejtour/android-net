package com.hll_sc_app.bean.order.statistic;


import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticBean {
    private int coopShopNum;
    private String purchaserName;
    private int shopNum;
    private String groupLogoUrl;
    private boolean showAll;
    private List<OrderStatisticShopBean> shopList;

    public String getGroupLogoUrl() {
        return groupLogoUrl;
    }

    public void setGroupLogoUrl(String groupLogoUrl) {
        this.groupLogoUrl = groupLogoUrl;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public int getCoopShopNum() {
        return coopShopNum;
    }

    public void setCoopShopNum(int coopShopNum) {
        this.coopShopNum = coopShopNum;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public List<OrderStatisticShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<OrderStatisticShopBean> shopList) {
        this.shopList = shopList;
    }
}
