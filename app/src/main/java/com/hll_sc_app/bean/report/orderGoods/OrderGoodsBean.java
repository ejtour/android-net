package com.hll_sc_app.bean.report.orderGoods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsBean implements Parcelable {
    private double inspectionNum;
    private double inspectionAmount;
    private double orderAmount;
    private double orderNum;
    private String shopName;
    private String shopID;
    private String purchaserName;
    private double skuNum;

    public double getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(double inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public double getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(double inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(double orderNum) {
        this.orderNum = orderNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(double skuNum) {
        this.skuNum = skuNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.inspectionNum);
        dest.writeDouble(this.inspectionAmount);
        dest.writeDouble(this.orderAmount);
        dest.writeDouble(this.orderNum);
        dest.writeString(this.shopName);
        dest.writeString(this.shopID);
        dest.writeString(this.purchaserName);
        dest.writeDouble(this.skuNum);
    }

    public OrderGoodsBean() {
    }

    protected OrderGoodsBean(Parcel in) {
        this.inspectionNum = in.readDouble();
        this.inspectionAmount = in.readDouble();
        this.orderAmount = in.readDouble();
        this.orderNum = in.readDouble();
        this.shopName = in.readString();
        this.shopID = in.readString();
        this.purchaserName = in.readString();
        this.skuNum = in.readDouble();
    }

    public static final Parcelable.Creator<OrderGoodsBean> CREATOR = new Parcelable.Creator<OrderGoodsBean>() {
        @Override
        public OrderGoodsBean createFromParcel(Parcel source) {
            return new OrderGoodsBean(source);
        }

        @Override
        public OrderGoodsBean[] newArray(int size) {
            return new OrderGoodsBean[size];
        }
    };
}
