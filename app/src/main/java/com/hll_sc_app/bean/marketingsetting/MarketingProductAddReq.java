package com.hll_sc_app.bean.marketingsetting;

import java.util.List;

/**
 * 新增营销商品请求
 */
public class MarketingProductAddReq {

    private String id;
    private int discountType;
    private int couponType;
    private int validityType;
    private String discountName;
    private int customerScope;
    private int couponCondition;
    private String discountStartTime;
    private String discountEndTime;
    private String validityDays;
    private int discountStage;
    private int discountRuleType;
    private int areaScope;
    private String groupID;
    private List<AddProductBean> productList;
    private List<AreaListBean> areaList;
    private List<RuleListBean> ruleList;
    private List<MarketingCustomerBean> customerList;

    public String getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(String validityDays) {
        this.validityDays = validityDays;
    }

    public int getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(int couponCondition) {
        this.couponCondition = couponCondition;
    }

    public int getValidityType() {
        return validityType;
    }

    public void setValidityType(int validityType) {
        this.validityType = validityType;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public int getCustomerScope() {
        return customerScope;
    }

    public void setCustomerScope(int customerScope) {
        this.customerScope = customerScope;
    }

    public String getDiscountStartTime() {
        return discountStartTime;
    }

    public void setDiscountStartTime(String discountStartTime) {
        this.discountStartTime = discountStartTime;
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

    public int getDiscountRuleType() {
        return discountRuleType;
    }

    public void setDiscountRuleType(int discountRuleType) {
        this.discountRuleType = discountRuleType;
    }

    public int getAreaScope() {
        return areaScope;
    }

    public void setAreaScope(int areaScope) {
        this.areaScope = areaScope;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<AddProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<AddProductBean> productList) {
        this.productList = productList;
    }

    public List<AreaListBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBean> areaList) {
        this.areaList = areaList;
    }

    public List<RuleListBean> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleListBean> ruleList) {
        this.ruleList = ruleList;
    }

    public List<MarketingCustomerBean> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<MarketingCustomerBean> customerList) {
        this.customerList = customerList;
    }

    public static class AddProductBean {
        private String productID;
        private String promoteNum;
        private String specID;
        private int nonRefund;

        public int getNonRefund() {
            return nonRefund;
        }

        public void setNonRefund(int nonRefund) {
            this.nonRefund = nonRefund;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getPromoteNum() {
            return promoteNum;
        }

        public void setPromoteNum(String promoteNum) {
            this.promoteNum = promoteNum;
        }

        public String getSpecID() {
            return specID;
        }

        public void setSpecID(String specID) {
            this.specID = specID;
        }
    }

}
