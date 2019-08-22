package com.hll_sc_app.bean.marketingsetting;

import java.util.List;

/**
 * 分发优惠券响应
 */
public class CouponSendResultBean {
    private String businessID;
    private List<String> couponIDList;
    private String discountID;
    private String msg;
    private int sendResult;

    public boolean isSuccess() {
        return sendResult == 0;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public List<String> getCouponIDList() {
        return couponIDList;
    }

    public void setCouponIDList(List<String> couponIDList) {
        this.couponIDList = couponIDList;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSendResult() {
        return sendResult;
    }

    public void setSendResult(int sendResult) {
        this.sendResult = sendResult;
    }
}
