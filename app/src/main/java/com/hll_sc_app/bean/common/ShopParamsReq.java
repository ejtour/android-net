package com.hll_sc_app.bean.common;

import java.util.List;

/**
 * 修改门店参数
 */
public class ShopParamsReq {
    private List<Param> bizList;
    private String groupID;
    private String purchaserID;
    private List<String> shopIDs;

    public List<Param> getBizList() {
        return bizList;
    }

    public void setBizList(List<Param> bizList) {
        this.bizList = bizList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public List<String> getShopIDs() {
        return shopIDs;
    }

    public void setShopIDs(List<String> shopIDs) {
        this.shopIDs = shopIDs;
    }

    public static class Param {
        public Param(String bizType, String bizValue) {
            this.bizType = bizType;
            this.bizValue = bizValue;
        }

        private String bizType;
        private String bizValue;

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getBizValue() {
            return bizValue;
        }

        public void setBizValue(String bizValue) {
            this.bizValue = bizValue;
        }
    }
}
