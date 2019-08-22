package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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

    public static class CouponBean  implements Parcelable {
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
        private String validityDays;
        private String couponValue;
        private String createBy;
        private int validityType;
        private String createTime;
        private String invalidCount;
        private String odmId;
        private List<Integer> opList;

        public String getValidityDays() {
            return validityDays;
        }

        public void setValidityDays(String validityDays) {
            this.validityDays = validityDays;
        }

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

        public CouponBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.actionTime);
            dest.writeInt(this.areaScope);
            dest.writeInt(this.discountRuleType);
            dest.writeString(this.couponCount);
            dest.writeString(this.discountName);
            dest.writeString(this.discountEndTime);
            dest.writeInt(this.couponType);
            dest.writeInt(this.discountStage);
            dest.writeString(this.discountStartTime);
            dest.writeString(this.areaDesc);
            dest.writeInt(this.discountStatus);
            dest.writeString(this.couponBatch);
            dest.writeInt(this.action);
            dest.writeInt(this.discountType);
            dest.writeString(this.id);
            dest.writeString(this.useCount);
            dest.writeString(this.customerScope);
            dest.writeString(this.discountStatusName);
            dest.writeString(this.actionBy);
            dest.writeString(this.ruleTypeName);
            dest.writeInt(this.couponCondition);
            dest.writeString(this.groupID);
            dest.writeString(this.unUseCount);
            dest.writeString(this.couponConditionValue);
            dest.writeString(this.sendCount);
            dest.writeString(this.customerDesc);
            dest.writeString(this.validityDays);
            dest.writeString(this.couponValue);
            dest.writeString(this.createBy);
            dest.writeInt(this.validityType);
            dest.writeString(this.createTime);
            dest.writeString(this.invalidCount);
            dest.writeString(this.odmId);
            dest.writeList(this.opList);
        }

        protected CouponBean(Parcel in) {
            this.actionTime = in.readString();
            this.areaScope = in.readInt();
            this.discountRuleType = in.readInt();
            this.couponCount = in.readString();
            this.discountName = in.readString();
            this.discountEndTime = in.readString();
            this.couponType = in.readInt();
            this.discountStage = in.readInt();
            this.discountStartTime = in.readString();
            this.areaDesc = in.readString();
            this.discountStatus = in.readInt();
            this.couponBatch = in.readString();
            this.action = in.readInt();
            this.discountType = in.readInt();
            this.id = in.readString();
            this.useCount = in.readString();
            this.customerScope = in.readString();
            this.discountStatusName = in.readString();
            this.actionBy = in.readString();
            this.ruleTypeName = in.readString();
            this.couponCondition = in.readInt();
            this.groupID = in.readString();
            this.unUseCount = in.readString();
            this.couponConditionValue = in.readString();
            this.sendCount = in.readString();
            this.customerDesc = in.readString();
            this.validityDays = in.readString();
            this.couponValue = in.readString();
            this.createBy = in.readString();
            this.validityType = in.readInt();
            this.createTime = in.readString();
            this.invalidCount = in.readString();
            this.odmId = in.readString();
            this.opList = new ArrayList<Integer>();
            in.readList(this.opList, Integer.class.getClassLoader());
        }

        public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
            @Override
            public CouponBean createFromParcel(Parcel source) {
                return new CouponBean(source);
            }

            @Override
            public CouponBean[] newArray(int size) {
                return new CouponBean[size];
            }
        };
    }
}
