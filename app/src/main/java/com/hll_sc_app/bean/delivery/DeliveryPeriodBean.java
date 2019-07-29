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
    /**
     * 截单时间
     */
    private String billUpDateTime;
    /**
     * 送达时间标识 0-当天 1-次日 2-第三天....6-第七天
     */
    private int dayTimeFlag;

    public int getDayTimeFlag() {
        return dayTimeFlag;
    }

    public void setDayTimeFlag(int dayTimeFlag) {
        this.dayTimeFlag = dayTimeFlag;
    }

    public String getBillUpDateTime() {
        return billUpDateTime;
    }

    public void setBillUpDateTime(String billUpDateTime) {
        this.billUpDateTime = billUpDateTime;
    }

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
