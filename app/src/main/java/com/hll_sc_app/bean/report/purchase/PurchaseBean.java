package com.hll_sc_app.bean.report.purchase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class PurchaseBean implements Parcelable {
    private String date;
    private int purchaserNum;
    private double logisticsCost;
    private int carNums;
    private String id;
    private transient double purchaserEfficiency;
    private double purchaseAmount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPurchaserNum() {
        return purchaserNum;
    }

    public void setPurchaserNum(int purchaserNum) {
        this.purchaserNum = purchaserNum;
    }

    public double getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(double logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public int getCarNums() {
        return carNums;
    }

    public void setCarNums(int carNums) {
        this.carNums = carNums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPurchaserEfficiency() {
        return purchaserEfficiency;
    }

    public void setPurchaserEfficiency(double purchaserEfficiency) {
        this.purchaserEfficiency = purchaserEfficiency;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeInt(this.purchaserNum);
        dest.writeDouble(this.logisticsCost);
        dest.writeInt(this.carNums);
        dest.writeString(this.id);
        dest.writeDouble(this.purchaseAmount);
    }

    public PurchaseBean deepCopy(){
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }

    public PurchaseBean() {
    }

    protected PurchaseBean(Parcel in) {
        this.date = in.readString();
        this.purchaserNum = in.readInt();
        this.logisticsCost = in.readDouble();
        this.carNums = in.readInt();
        this.id = in.readString();
        this.purchaseAmount = in.readDouble();
    }

    public static final Parcelable.Creator<PurchaseBean> CREATOR = new Parcelable.Creator<PurchaseBean>() {
        @Override
        public PurchaseBean createFromParcel(Parcel source) {
            return new PurchaseBean(source);
        }

        @Override
        public PurchaseBean[] newArray(int size) {
            return new PurchaseBean[size];
        }
    };
}
