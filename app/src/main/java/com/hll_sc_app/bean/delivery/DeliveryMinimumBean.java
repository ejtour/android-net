package com.hll_sc_app.bean.delivery;

/**
 * 起送金额
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class DeliveryMinimumBean {
    /**
     * 设置类型 0-地区 1-采购商
     */
    private String settings;
    private String supplyName;
    private String actionTime;
    private String createBy;
    private String sendPrice;
    private String actionBy;
    private String createTime;
    private String supplyShopID;
    private String supplyID;
    private String divideName;
    private String areaNum;
    private String sendAmountID;

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(String sendPrice) {
        this.sendPrice = sendPrice;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public String getDivideName() {
        return divideName;
    }

    public void setDivideName(String divideName) {
        this.divideName = divideName;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getSendAmountID() {
        return sendAmountID;
    }

    public void setSendAmountID(String sendAmountID) {
        this.sendAmountID = sendAmountID;
    }
}
