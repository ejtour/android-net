package com.hll_sc_app.bean.aftersales;

import java.util.List;

public class NegotiationHistoryResp {

    private List<NegotiationHistoryBean> list;

    public List<NegotiationHistoryBean> getList() {
        return list;
    }

    public void setList(List<NegotiationHistoryBean> list) {
        this.list = list;
    }

    public static class NegotiationHistoryBean {
        private String handleBy;
        private String reason;
        private long actionTime;
        private String shopName;
        private int originator;
        private int refundBillID;
        private String supplyShopName;
        private String createby;
        private int purchaserID;
        private int refundBillType;
        private int action;
        private int id;
        private double refundAmount;
        private String spareField4;
        private String spareField3;
        private long handleTime;
        private String actionBy;
        private int supplyShopID;
        private int subBillID;
        private int groupID;
        private String handleLog;
        private String logoUrl;
        private String purchaserName;
        private String groupName;
        private long createTime;
        private int handleType;
        private int shopID;
        private String spareField2;
        private String showLog;
        private String spareField1;

        public String getHandleBy() {
            return handleBy;
        }

        public void setHandleBy(String handleBy) {
            this.handleBy = handleBy;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public long getActionTime() {
            return actionTime;
        }

        public void setActionTime(long actionTime) {
            this.actionTime = actionTime;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getOriginator() {
            return originator;
        }

        public void setOriginator(int originator) {
            this.originator = originator;
        }

        public int getRefundBillID() {
            return refundBillID;
        }

        public void setRefundBillID(int refundBillID) {
            this.refundBillID = refundBillID;
        }

        public String getSupplyShopName() {
            return supplyShopName;
        }

        public void setSupplyShopName(String supplyShopName) {
            this.supplyShopName = supplyShopName;
        }

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public int getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(int purchaserID) {
            this.purchaserID = purchaserID;
        }

        public int getRefundBillType() {
            return refundBillType;
        }

        public void setRefundBillType(int refundBillType) {
            this.refundBillType = refundBillType;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(double refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getSpareField4() {
            return spareField4;
        }

        public void setSpareField4(String spareField4) {
            this.spareField4 = spareField4;
        }

        public String getSpareField3() {
            return spareField3;
        }

        public void setSpareField3(String spareField3) {
            this.spareField3 = spareField3;
        }

        public long getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(long handleTime) {
            this.handleTime = handleTime;
        }

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
        }

        public int getSupplyShopID() {
            return supplyShopID;
        }

        public void setSupplyShopID(int supplyShopID) {
            this.supplyShopID = supplyShopID;
        }

        public int getSubBillID() {
            return subBillID;
        }

        public void setSubBillID(int subBillID) {
            this.subBillID = subBillID;
        }

        public int getGroupID() {
            return groupID;
        }

        public void setGroupID(int groupID) {
            this.groupID = groupID;
        }

        public String getHandleLog() {
            return handleLog;
        }

        public void setHandleLog(String handleLog) {
            this.handleLog = handleLog;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getHandleType() {
            return handleType;
        }

        public void setHandleType(int handleType) {
            this.handleType = handleType;
        }

        public int getShopID() {
            return shopID;
        }

        public void setShopID(int shopID) {
            this.shopID = shopID;
        }

        public String getSpareField2() {
            return spareField2;
        }

        public void setSpareField2(String spareField2) {
            this.spareField2 = spareField2;
        }

        public String getShowLog() {
            return showLog;
        }

        public void setShowLog(String showLog) {
            this.showLog = showLog;
        }

        public String getSpareField1() {
            return spareField1;
        }

        public void setSpareField1(String spareField1) {
            this.spareField1 = spareField1;
        }
    }
}
