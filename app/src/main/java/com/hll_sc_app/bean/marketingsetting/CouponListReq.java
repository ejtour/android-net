package com.hll_sc_app.bean.marketingsetting;

/**
 * 选择优惠券列表
 */
public class CouponListReq {

    private int discountType;
    private String groupID;

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
