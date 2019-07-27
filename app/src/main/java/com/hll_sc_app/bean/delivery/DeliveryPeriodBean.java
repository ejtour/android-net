package com.hll_sc_app.bean.delivery;

/**
 * 配送时段列表查询
 *
 * @author zhuyingsong
 * @date 2019-07-19
 */
public class DeliveryPeriodBean {
    /**
     * 送达截止时间
     */
    private String arrivalEndTime;
    /**
     * 送达开始时间
     */
    private String arrivalStartTime;
    /**
     * 配送时段id
     */
    private String deliveryTimeID;

    public String getArrivalEndTime() {
        return arrivalEndTime;
    }

    public void setArrivalEndTime(String arrivalEndTime) {
        this.arrivalEndTime = arrivalEndTime;
    }

    public String getArrivalStartTime() {
        return arrivalStartTime;
    }

    public void setArrivalStartTime(String arrivalStartTime) {
        this.arrivalStartTime = arrivalStartTime;
    }

    public String getDeliveryTimeID() {
        return deliveryTimeID;
    }

    public void setDeliveryTimeID(String deliveryTimeID) {
        this.deliveryTimeID = deliveryTimeID;
    }
}
