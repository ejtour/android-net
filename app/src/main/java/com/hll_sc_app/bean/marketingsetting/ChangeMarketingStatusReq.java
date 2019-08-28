package com.hll_sc_app.bean.marketingsetting;

/**
 * 改变促销状态
 */
public class ChangeMarketingStatusReq {
    private String discountStatus;
    private String id;

    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
