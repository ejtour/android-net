package com.hll_sc_app.bean.marketingsetting;

import java.util.List;

/**
 * 优惠券列表响应
 */
public class CouponListResp {

    private int totalSize;
    private List<CouponBean> list;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CouponBean> getList() {
        return list;
    }

    public void setList(List<CouponBean> list) {
        this.list = list;
    }

    public static class CouponBean {
        /**
         * 未开始
         */
        static public int STATUS_INIT = 1;
        /**
         * 促销中
         */
        static public int STATUS_PROMOTION = 2;
        /**
         * 已失效
         */
        static public int STATUS_EXPIRED = 3;
        /**
         * 已作废
         */
        static public int STATUS_INVALID = 4;
        /**
         * 已暂停
         */
        static public int STATUS_PAUSE = 5;
        /**
         * 修改
         */
        static public int OPTION_MDY = 1;
        /**
         * 失效
         */
        static public int OPTION_INVALID = 2;
        /**
         * 启用
         */
        static public int OPTION_ENABLE = 3;
        /**
         * 暂停
         */
        static public int OPTION_PAUSE = 4;
        /**
         * 发放
         */
        static public int OPTION_RELEASE = 5;
        private String actionTime;
        private int areaScope;
        private int discountRuleType;
        private String couponCount;
        private String discountName;
        private String discountEndTime;
        private int couponType;
        private int discountStage;
        private String discountStartTime;
        private String areaDesc;
        private int discountStatus;
        private String couponBatch;
        private int action;
        private int discountType;
        private String id;
        private String useCount;
        private String customerScope;
        private String discountStatusName;
        private String actionBy;
        private String ruleTypeName;
        private int couponCondition;
        private String groupID;
        private String unUseCount;
        private String couponConditionValue;
        private String sendCount;
        private String customerDesc;
        private String couponValue;
        private String createBy;
        private int validityType;
        private String createTime;
        private String invalidCount;
        private String odmId;
        private List<Integer> opList;

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public int getAreaScope() {
            return areaScope;
        }

        public void setAreaScope(int areaScope) {
            this.areaScope = areaScope;
        }

        public int getDiscountRuleType() {
            return discountRuleType;
        }

        public void setDiscountRuleType(int discountRuleType) {
            this.discountRuleType = discountRuleType;
        }

        public String getCouponCount() {
            return couponCount;
        }

        public void setCouponCount(String couponCount) {
            this.couponCount = couponCount;
        }

        public String getDiscountName() {
            return discountName;
        }

        public void setDiscountName(String discountName) {
            this.discountName = discountName;
        }

        public String getDiscountEndTime() {
            return discountEndTime;
        }

        public void setDiscountEndTime(String discountEndTime) {
            this.discountEndTime = discountEndTime;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public int getDiscountStage() {
            return discountStage;
        }

        public void setDiscountStage(int discountStage) {
            this.discountStage = discountStage;
        }

        public String getDiscountStartTime() {
            return discountStartTime;
        }

        public void setDiscountStartTime(String discountStartTime) {
            this.discountStartTime = discountStartTime;
        }

        public String getAreaDesc() {
            return areaDesc;
        }

        public void setAreaDesc(String areaDesc) {
            this.areaDesc = areaDesc;
        }

        public int getDiscountStatus() {
            return discountStatus;
        }

        public void setDiscountStatus(int discountStatus) {
            this.discountStatus = discountStatus;
        }

        public String getCouponBatch() {
            return couponBatch;
        }

        public void setCouponBatch(String couponBatch) {
            this.couponBatch = couponBatch;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public int getDiscountType() {
            return discountType;
        }

        public void setDiscountType(int discountType) {
            this.discountType = discountType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUseCount() {
            return useCount;
        }

        public void setUseCount(String useCount) {
            this.useCount = useCount;
        }

        public String getCustomerScope() {
            return customerScope;
        }

        public void setCustomerScope(String customerScope) {
            this.customerScope = customerScope;
        }

        public String getDiscountStatusName() {
            return discountStatusName;
        }

        public void setDiscountStatusName(String discountStatusName) {
            this.discountStatusName = discountStatusName;
        }

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
        }

        public String getRuleTypeName() {
            return ruleTypeName;
        }

        public void setRuleTypeName(String ruleTypeName) {
            this.ruleTypeName = ruleTypeName;
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

        public String getUnUseCount() {
            return unUseCount;
        }

        public void setUnUseCount(String unUseCount) {
            this.unUseCount = unUseCount;
        }

        public String getCouponConditionValue() {
            return couponConditionValue;
        }

        public void setCouponConditionValue(String couponConditionValue) {
            this.couponConditionValue = couponConditionValue;
        }

        public String getSendCount() {
            return sendCount;
        }

        public void setSendCount(String sendCount) {
            this.sendCount = sendCount;
        }

        public String getCustomerDesc() {
            return customerDesc;
        }

        public void setCustomerDesc(String customerDesc) {
            this.customerDesc = customerDesc;
        }

        public String getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(String couponValue) {
            this.couponValue = couponValue;
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

        public String getInvalidCount() {
            return invalidCount;
        }

        public void setInvalidCount(String invalidCount) {
            this.invalidCount = invalidCount;
        }

        public String getOdmId() {
            return odmId;
        }

        public void setOdmId(String odmId) {
            this.odmId = odmId;
        }

        public List<Integer> getOpList() {
            return opList;
        }

        public void setOpList(List<Integer> opList) {
            this.opList = opList;
        }
    }
}
