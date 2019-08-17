package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.goods.SkuGoodsBean;

import java.util.ArrayList;
import java.util.List;

public class MarketingDetailCheckResp implements Parcelable {
    public static final Creator<MarketingDetailCheckResp> CREATOR = new Creator<MarketingDetailCheckResp>() {
        @Override
        public MarketingDetailCheckResp createFromParcel(Parcel source) {
            return new MarketingDetailCheckResp(source);
        }

        @Override
        public MarketingDetailCheckResp[] newArray(int size) {
            return new MarketingDetailCheckResp[size];
        }
    };
    private String actionTime;
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
    private String useCount;
    private int customerScope;
    private String discountStatusName;
    private String actionBy;
    private String ruleTypeName;
    private String groupID;
    private String unUseCount;
    private String sendCount;
    private String customerDesc;
    private String createBy;
    private String validityType;
    private String createTime;
    private String invalidCount;
    private String odmId;
    private List<RuleListBean> ruleList;
    private List<AreaListBean> areaList;
    private List<Integer> opList;
    private List<SkuGoodsBean> productList;

    public MarketingDetailCheckResp() {
    }

    protected MarketingDetailCheckResp(Parcel in) {
        this.actionTime = in.readString();
        this.areaScope = in.readInt();
        this.discountRuleType = in.readInt();
        this.couponCount = in.readString();
        this.productCount = in.readString();
        this.discountName = in.readString();
        this.discountEndTime = in.readString();
        this.discountStage = in.readInt();
        this.discountStartTime = in.readString();
        this.areaDesc = in.readString();
        this.discountStatus = in.readInt();
        this.action = in.readInt();
        this.discountType = in.readInt();
        this.id = in.readString();
        this.useCount = in.readString();
        this.customerScope = in.readInt();
        this.discountStatusName = in.readString();
        this.actionBy = in.readString();
        this.ruleTypeName = in.readString();
        this.groupID = in.readString();
        this.unUseCount = in.readString();
        this.sendCount = in.readString();
        this.customerDesc = in.readString();
        this.createBy = in.readString();
        this.validityType = in.readString();
        this.createTime = in.readString();
        this.invalidCount = in.readString();
        this.odmId = in.readString();
        this.ruleList = new ArrayList<RuleListBean>();
        in.readList(this.ruleList, RuleListBean.class.getClassLoader());
        this.areaList = new ArrayList<AreaListBean>();
        in.readList(this.areaList, AreaListBean.class.getClassLoader());
        this.opList = new ArrayList<Integer>();
        in.readList(this.opList, Integer.class.getClassLoader());
        this.productList = in.createTypedArrayList(SkuGoodsBean.CREATOR);
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

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
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

    public String getUnUseCount() {
        return unUseCount;
    }

    public void setUnUseCount(String unUseCount) {
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
        dest.writeString(this.productCount);
        dest.writeString(this.discountName);
        dest.writeString(this.discountEndTime);
        dest.writeInt(this.discountStage);
        dest.writeString(this.discountStartTime);
        dest.writeString(this.areaDesc);
        dest.writeInt(this.discountStatus);
        dest.writeInt(this.action);
        dest.writeInt(this.discountType);
        dest.writeString(this.id);
        dest.writeString(this.useCount);
        dest.writeInt(this.customerScope);
        dest.writeString(this.discountStatusName);
        dest.writeString(this.actionBy);
        dest.writeString(this.ruleTypeName);
        dest.writeString(this.groupID);
        dest.writeString(this.unUseCount);
        dest.writeString(this.sendCount);
        dest.writeString(this.customerDesc);
        dest.writeString(this.createBy);
        dest.writeString(this.validityType);
        dest.writeString(this.createTime);
        dest.writeString(this.invalidCount);
        dest.writeString(this.odmId);
        dest.writeList(this.ruleList);
        dest.writeList(this.areaList);
        dest.writeList(this.opList);
        dest.writeTypedList(this.productList);
    }
}
