package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 选择优惠券列表
 */
public class CouponListBean implements Parcelable {


    private String discountName;
    private int couponCondition;
    private int couponConditionValue;
    private String discountID;
    private double couponValue;

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public int getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(int couponCondition) {
        this.couponCondition = couponCondition;
    }

    public int getCouponConditionValue() {
        return couponConditionValue;
    }

    public void setCouponConditionValue(int couponConditionValue) {
        this.couponConditionValue = couponConditionValue;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(double couponValue) {
        this.couponValue = couponValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.discountName);
        dest.writeInt(this.couponCondition);
        dest.writeInt(this.couponConditionValue);
        dest.writeString(this.discountID);
        dest.writeDouble(this.couponValue);
    }

    public CouponListBean() {
    }

    protected CouponListBean(Parcel in) {
        this.discountName = in.readString();
        this.couponCondition = in.readInt();
        this.couponConditionValue = in.readInt();
        this.discountID = in.readString();
        this.couponValue = in.readDouble();
    }

    public static final Creator<CouponListBean> CREATOR = new Creator<CouponListBean>() {
        @Override
        public CouponListBean createFromParcel(Parcel source) {
            return new CouponListBean(source);
        }

        @Override
        public CouponListBean[] newArray(int size) {
            return new CouponListBean[size];
        }
    };
}
