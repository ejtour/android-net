package com.hll_sc_app.bean.marketingsetting;

import java.util.List;

/**
 * 优惠券使用详情列表响应
 */
public class CouponUseDetailListResp {
    private int totalSize;
    private List<CouponUseBean> list;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CouponUseBean> getList() {
        return list;
    }

    public void setList(List<CouponUseBean> list) {
        this.list = list;
    }

    public static class CouponUseBean {
        private int isShopActive;
        private String actionTime;
        private String couponUser;
        private String shopName;
        private String businessNo;
        private String discountName;
        private String purchaserID;
        private int couponType;
        private String validityEndTime;
        private String couponBatch;
        private int couponStatus;
        private int action;
        private String useTime;
        private String id;
        private String discountID;
        private int validityDays;
        private String coupon;
        private String actionBy;
        private int couponCondition;
        private String groupID;
        private int couponConditionValue;
        private int version;
        private String couponValue;
        private String purchaserName;
        private String sendTime;
        private String createBy;
        private int validityType;
        private String createTime;
        private String odmId;
        private int sendType;
        private String validityStartTime;
        private String shopID;

        public int getIsShopActive() {
            return isShopActive;
        }

        public void setIsShopActive(int isShopActive) {
            this.isShopActive = isShopActive;
        }

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public String getCouponUser() {
            return couponUser;
        }

        public void setCouponUser(String couponUser) {
            this.couponUser = couponUser;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public String getDiscountName() {
            return discountName;
        }

        public void setDiscountName(String discountName) {
            this.discountName = discountName;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public String getValidityEndTime() {
            return validityEndTime;
        }

        public void setValidityEndTime(String validityEndTime) {
            this.validityEndTime = validityEndTime;
        }

        public String getCouponBatch() {
            return couponBatch;
        }

        public void setCouponBatch(String couponBatch) {
            this.couponBatch = couponBatch;
        }

        public int getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(int couponStatus) {
            this.couponStatus = couponStatus;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiscountID() {
            return discountID;
        }

        public void setDiscountID(String discountID) {
            this.discountID = discountID;
        }

        public int getValidityDays() {
            return validityDays;
        }

        public void setValidityDays(int validityDays) {
            this.validityDays = validityDays;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
        }

        public int getCouponCondition() {
            return couponCondition;
        }

        public void setCouponCondition(int couponCondition) {
            this.couponCondition = couponCondition;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public int getCouponConditionValue() {
            return couponConditionValue;
        }

        public void setCouponConditionValue(int couponConditionValue) {
            this.couponConditionValue = couponConditionValue;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(String couponValue) {
            this.couponValue = couponValue;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public int getValidityType() {
            return validityType;
        }

        public void setValidityType(int validityType) {
            this.validityType = validityType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOdmId() {
            return odmId;
        }

        public void setOdmId(String odmId) {
            this.odmId = odmId;
        }

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public String getValidityStartTime() {
            return validityStartTime;
        }

        public void setValidityStartTime(String validityStartTime) {
            this.validityStartTime = validityStartTime;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }
    }
}
