package com.hll_sc_app.bean.cooperation;

import java.util.List;

public class ChangeGroupParamReq {
    //是否修改全部门店数据（0-否，1-是）
    private int changeAllShops;
    private String groupID;
    private String purchaserID;
    private List<BizList> bizList;

    public int getChangeAllShops() {
        return changeAllShops;
    }

    public void setChangeAllShops(int changeAllShops) {
        this.changeAllShops = changeAllShops;
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

    public List<BizList> getBizList() {
        return bizList;
    }

    public void setBizList(List<BizList> bizList) {
        this.bizList = bizList;
    }

    public static class BizList {
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
