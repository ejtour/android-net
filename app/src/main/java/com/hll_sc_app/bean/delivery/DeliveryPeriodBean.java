package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 配送时段列表查询
 *
 * @author zhuyingsong
 * @date 2019-07-19
 */
public class DeliveryPeriodBean implements Parcelable {
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

    public static final Parcelable.Creator<DeliveryPeriodBean> CREATOR = new Parcelable.Creator<DeliveryPeriodBean>() {
        @Override
        public DeliveryPeriodBean createFromParcel(Parcel source) {
            return new DeliveryPeriodBean(source);
        }

        @Override
        public DeliveryPeriodBean[] newArray(int size) {
            return new DeliveryPeriodBean[size];
        }
    };

    public DeliveryPeriodBean() {
    }

    protected DeliveryPeriodBean(Parcel in) {
        this.arrivalEndTime = in.readString();
        this.arrivalStartTime = in.readString();
        this.deliveryTimeID = in.readString();
        this.billUpDateTime = in.readString();
        this.dayTimeFlag = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.arrivalEndTime);
        dest.writeString(this.arrivalStartTime);
        dest.writeString(this.deliveryTimeID);
        dest.writeString(this.billUpDateTime);
        dest.writeInt(this.dayTimeFlag);
    }
}
