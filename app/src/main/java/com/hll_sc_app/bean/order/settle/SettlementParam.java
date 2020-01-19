package com.hll_sc_app.bean.order.settle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public class SettlementParam implements Parcelable {
    private double totalPrice;
    private int payType;
    private String subBillID;
    private List<PayWaysReq.GroupList> groupLists;

    public List<PayWaysReq.GroupList> getGroupLists() {
        return groupLists;
    }

    public void setGroupLists(List<PayWaysReq.GroupList> groupLists) {
        this.groupLists = groupLists;
    }

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

    public SettlementParam() {
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
        dest.writeTypedList(this.groupLists);
    }

    protected SettlementParam(Parcel in) {
        this.totalPrice = in.readDouble();
        this.payType = in.readInt();
        this.subBillID = in.readString();
        this.groupLists = in.createTypedArrayList(PayWaysReq.GroupList.CREATOR);
    }

    public static final Creator<SettlementParam> CREATOR = new Creator<SettlementParam>() {
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
