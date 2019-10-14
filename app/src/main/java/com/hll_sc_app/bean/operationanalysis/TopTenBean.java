package com.hll_sc_app.bean.operationanalysis;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class TopTenBean {
    private String groupID;
    private String groupName;
    private String shopID;
    private String shopName;
    private int validOrderNum;
    private double validTradeAmount;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public int getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(int validOrderNum) {
        this.validOrderNum = validOrderNum;
    }

    public double getValidTradeAmount() {
        return validTradeAmount;
    }

    public void setValidTradeAmount(double validTradeAmount) {
        this.validTradeAmount = validTradeAmount;
    }
}
