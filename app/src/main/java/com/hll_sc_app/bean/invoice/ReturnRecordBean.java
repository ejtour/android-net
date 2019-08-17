package com.hll_sc_app.bean.invoice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/15
 */

public class ReturnRecordBean implements Parcelable {
    private String actionTime;
    private String createby;
    private String returnDate;
    private String actionBy;
    private String createTime;
    private int settlementStatus;
    private int returnPayType;
    private int action;
    private double returnMoney;
    private String invoiceID;
    private String id;
    private String userID;

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(int settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public int getReturnPayType() {
        return returnPayType;
    }

    public void setReturnPayType(int returnPayType) {
        this.returnPayType = returnPayType;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.createby);
        dest.writeString(this.returnDate);
        dest.writeString(this.actionBy);
        dest.writeString(this.createTime);
        dest.writeInt(this.settlementStatus);
        dest.writeInt(this.returnPayType);
        dest.writeInt(this.action);
        dest.writeDouble(this.returnMoney);
        dest.writeString(this.invoiceID);
        dest.writeString(this.id);
        dest.writeString(this.userID);
    }

    public ReturnRecordBean() {
    }

    protected ReturnRecordBean(Parcel in) {
        this.actionTime = in.readString();
        this.createby = in.readString();
        this.returnDate = in.readString();
        this.actionBy = in.readString();
        this.createTime = in.readString();
        this.settlementStatus = in.readInt();
        this.returnPayType = in.readInt();
        this.action = in.readInt();
        this.returnMoney = in.readDouble();
        this.invoiceID = in.readString();
        this.id = in.readString();
        this.userID = in.readString();
    }

    public static final Parcelable.Creator<ReturnRecordBean> CREATOR = new Parcelable.Creator<ReturnRecordBean>() {
        @Override
        public ReturnRecordBean createFromParcel(Parcel source) {
            return new ReturnRecordBean(source);
        }

        @Override
        public ReturnRecordBean[] newArray(int size) {
            return new ReturnRecordBean[size];
        }
    };
}
