package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 报价生效时间
 *
 * @author zhuyingsong
 * @date 2019-07-11
 */
public class TimeBean implements Parcelable {
    private String endDate;
    private double price;
    private String activeTime;
    private int isActive;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public static final Parcelable.Creator<TimeBean> CREATOR = new Parcelable.Creator<TimeBean>() {
        @Override
        public TimeBean createFromParcel(Parcel source) {
            return new TimeBean(source);
        }

        @Override
        public TimeBean[] newArray(int size) {
            return new TimeBean[size];
        }
    };

    public TimeBean() {
    }

    protected TimeBean(Parcel in) {
        this.endDate = in.readString();
        this.price = in.readDouble();
        this.activeTime = in.readString();
        this.isActive = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.endDate);
        dest.writeDouble(this.price);
        dest.writeString(this.activeTime);
        dest.writeInt(this.isActive);
    }
}
