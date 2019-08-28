package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RuleListBean implements Parcelable {


    private String actionTime;
    private String actionBy;
    private String ruleCondition;
    private String ruleStatus;
    private String createBy;
    private String createTime;
    private int ruleType;
    private String odmId;
    private int action;
    private String ruleDiscountValue;
    private String ruleName;
    private String id;
    private String discountID;
    private List<GiveBean> giveList;
    private String giveTarget;
    private String couponValue;
    private int giveType;

    public String getGiveTarget() {
        return giveTarget;
    }

    public void setGiveTarget(String giveTarget) {
        this.giveTarget = giveTarget;
    }

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    public int getGiveType() {
        return giveType;
    }

    public void setGiveType(int giveType) {
        this.giveType = giveType;
    }

    public List<GiveBean> getGiveList() {
        return giveList;
    }

    public void setGiveList(List<GiveBean> giveList) {
        this.giveList = giveList;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getRuleDiscountValue() {
        return ruleDiscountValue;
    }

    public void setRuleDiscountValue(String ruleDiscountValue) {
        this.ruleDiscountValue = ruleDiscountValue;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.actionBy);
        dest.writeString(this.ruleCondition);
        dest.writeString(this.ruleStatus);
        dest.writeString(this.createBy);
        dest.writeString(this.createTime);
        dest.writeInt(this.ruleType);
        dest.writeString(this.odmId);
        dest.writeInt(this.action);
        dest.writeString(this.ruleDiscountValue);
        dest.writeString(this.ruleName);
        dest.writeString(this.id);
        dest.writeString(this.discountID);
        dest.writeList(this.giveList);
        dest.writeString(this.giveTarget);
        dest.writeString(this.couponValue);
        dest.writeInt(this.giveType);
    }

    public RuleListBean() {
    }

    protected RuleListBean(Parcel in) {
        this.actionTime = in.readString();
        this.actionBy = in.readString();
        this.ruleCondition = in.readString();
        this.ruleStatus = in.readString();
        this.createBy = in.readString();
        this.createTime = in.readString();
        this.ruleType = in.readInt();
        this.odmId = in.readString();
        this.action = in.readInt();
        this.ruleDiscountValue = in.readString();
        this.ruleName = in.readString();
        this.id = in.readString();
        this.discountID = in.readString();
        this.giveList = new ArrayList<GiveBean>();
        in.readList(this.giveList, GiveBean.class.getClassLoader());
        this.giveTarget = in.readString();
        this.couponValue = in.readString();
        this.giveType = in.readInt();
    }

    public static final Creator<RuleListBean> CREATOR = new Creator<RuleListBean>() {
        @Override
        public RuleListBean createFromParcel(Parcel source) {
            return new RuleListBean(source);
        }

        @Override
        public RuleListBean[] newArray(int size) {
            return new RuleListBean[size];
        }
    };
}
