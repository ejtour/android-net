package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.goods.SkuGoodsBean;

import java.util.List;

public class MarketingDetailCheckResp implements Parcelable {
    private String actionTime;
    private String couponValue;
    private int areaScope;
    private int discountRuleType;
    private String couponCount;
    private String productCount;
    private String discountName;
    private String discountEndTime;
    private int discountStage;
    private String discountStartTime;
    private String areaDesc;
    private int discountStatus;
    private int action;
    private int discountType;
    private String id;
    private int useCount;
    private int customerScope;
    private String discountStatusName;
    private String actionBy;
    private String ruleTypeName;
    private String groupID;
    private int unUseCount;
    private String sendCount;
    private String customerDesc;
    private String createBy;
    private String couponCondition;
    private String validityType;
    private String validityDays;
    private String createTime;
    private List<MarketingCustomerBean> customerList;

    protected MarketingDetailCheckResp(Parcel in) {
        actionTime = in.readString();
        couponValue = in.readString();
        areaScope = in.readInt();
        discountRuleType = in.readInt();
        couponCount = in.readString();
        productCount = in.readString();
        discountName = in.readString();
        discountEndTime = in.readString();
        discountStage = in.readInt();
        discountStartTime = in.readString();
        areaDesc = in.readString();
        discountStatus = in.readInt();
        action = in.readInt();
        discountType = in.readInt();
        id = in.readString();
        useCount = in.readInt();
        customerScope = in.readInt();
        discountStatusName = in.readString();
        actionBy = in.readString();
        ruleTypeName = in.readString();
        groupID = in.readString();
        unUseCount = in.readInt();
        sendCount = in.readString();
        customerDesc = in.readString();
        createBy = in.readString();
        couponCondition = in.readString();
        validityType = in.readString();
        validityDays = in.readString();
        createTime = in.readString();
        customerList = in.createTypedArrayList(MarketingCustomerBean.CREATOR);
        invalidCount = in.readInt();
        odmId = in.readString();
        ruleList = in.createTypedArrayList(RuleListBean.CREATOR);
        areaList = in.createTypedArrayList(AreaListBean.CREATOR);
        productList = in.createTypedArrayList(SkuGoodsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actionTime);
        dest.writeString(couponValue);
        dest.writeInt(areaScope);
        dest.writeInt(discountRuleType);
        dest.writeString(couponCount);
        dest.writeString(productCount);
        dest.writeString(discountName);
        dest.writeString(discountEndTime);
        dest.writeInt(discountStage);
        dest.writeString(discountStartTime);
        dest.writeString(areaDesc);
        dest.writeInt(discountStatus);
        dest.writeInt(action);
        dest.writeInt(discountType);
        dest.writeString(id);
        dest.writeInt(useCount);
        dest.writeInt(customerScope);
        dest.writeString(discountStatusName);
        dest.writeString(actionBy);
        dest.writeString(ruleTypeName);
        dest.writeString(groupID);
        dest.writeInt(unUseCount);
        dest.writeString(sendCount);
        dest.writeString(customerDesc);
        dest.writeString(createBy);
        dest.writeString(couponCondition);
        dest.writeString(validityType);
        dest.writeString(validityDays);
        dest.writeString(createTime);
        dest.writeTypedList(customerList);
        dest.writeInt(invalidCount);
        dest.writeString(odmId);
        dest.writeTypedList(ruleList);
        dest.writeTypedList(areaList);
        dest.writeTypedList(productList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MarketingDetailCheckResp> CREATOR = new Creator<MarketingDetailCheckResp>() {
        @Override
        public MarketingDetailCheckResp createFromParcel(Parcel in) {
            return new MarketingDetailCheckResp(in);
        }

        @Override
        public MarketingDetailCheckResp[] newArray(int size) {
            return new MarketingDetailCheckResp[size];
        }
    };

    public String getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(String couponCondition) {
        this.couponCondition = couponCondition;
    }

    public String getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(String validityDays) {
        this.validityDays = validityDays;
    }

    private int invalidCount;
    private String odmId;
    private List<RuleListBean> ruleList;
    private List<AreaListBean> areaList;
    private List<Integer> opList;
    private List<SkuGoodsBean> productList;

    public MarketingDetailCheckResp() {
    }

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
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

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
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

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getCustomerScope() {
        return customerScope;
    }

    public void setCustomerScope(int customerScope) {
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

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getUnUseCount() {
        return unUseCount;
    }

    public void setUnUseCount(int unUseCount) {
        this.unUseCount = unUseCount;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getValidityType() {
        return validityType;
    }

    public void setValidityType(String validityType) {
        this.validityType = validityType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(int invalidCount) {
        this.invalidCount = invalidCount;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public List<RuleListBean> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleListBean> ruleList) {
        this.ruleList = ruleList;
    }

    public List<AreaListBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBean> areaList) {
        this.areaList = areaList;
    }

    public List<Integer> getOpList() {
        return opList;
    }

    public void setOpList(List<Integer> opList) {
        this.opList = opList;
    }

    public List<SkuGoodsBean> getProductList() {
        return productList;
    }

    public void setProductList(List<SkuGoodsBean> productList) {
        this.productList = productList;
    }

    public List<MarketingCustomerBean> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<MarketingCustomerBean> customerList) {
        this.customerList = customerList;
    }

}
