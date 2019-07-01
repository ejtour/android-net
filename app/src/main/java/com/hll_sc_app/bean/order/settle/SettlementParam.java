package com.hll_sc_app.bean.order.settle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public class SettlementParam implements Parcelable {
    private double totalPrice;
    private int payType;
    private String subBillID;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.totalPrice);
        dest.writeInt(this.payType);
        dest.writeString(this.subBillID);
    }

    public SettlementParam() {
    }

    protected SettlementParam(Parcel in) {
        this.totalPrice = in.readDouble();
        this.payType = in.readInt();
        this.subBillID = in.readString();
    }

    public static final Parcelable.Creator<SettlementParam> CREATOR = new Parcelable.Creator<SettlementParam>() {
        @Override
        public SettlementParam createFromParcel(Parcel source) {
            return new SettlementParam(source);
        }

        @Override
        public SettlementParam[] newArray(int size) {
            return new SettlementParam[size];
        }
    };
}
