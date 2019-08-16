package com.hll_sc_app.bean.marketingsetting;

/**
 * 促销活动查看接口
 */
public class MarketingDetailCheckReq {
    private String actionType;
    private String discountID;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }
}
