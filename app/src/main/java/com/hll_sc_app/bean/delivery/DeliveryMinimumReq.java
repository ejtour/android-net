package com.hll_sc_app.bean.delivery;

import java.util.List;

/**
 * 保存起送金额
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class DeliveryMinimumReq {
    private String name;
    private String amount;
    private String settings;
    private String sendAmountID;
    private String supplyID;
    private String supplyShopID;
    private String type;
    private List<String> codeList;
    private List<DeliveryPurchaserBean> purchaserList;

    public List<DeliveryPurchaserBean> getPurchaserList() {
        return purchaserList;
    }

    public void setPurchaserList(List<DeliveryPurchaserBean> purchaserList) {
        this.purchaserList = purchaserList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getSendAmountID() {
        return sendAmountID;
    }

    public void setSendAmountID(String sendAmountID) {
        this.sendAmountID = sendAmountID;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }
}
