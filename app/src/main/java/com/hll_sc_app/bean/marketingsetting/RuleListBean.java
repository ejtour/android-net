package com.hll_sc_app.bean.marketingsetting;

import java.util.List;

public class RuleListBean {


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
}
