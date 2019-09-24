package com.hll_sc_app.bean.order.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public class OrderDepositBean implements Parcelable {
    private double depositNum;
    private int detailID;
    private int productID;
    private String productName;
    @SerializedName(value = "productNum", alternate = "shopcartNum")
    private double productNum;
    private transient double rawProductNum;
    private double productPrice;
    private String productSpec;
    private int productSpecID;
    private double replenishmentNum;
    private String saleUnitName;
    private double subtotalAmount;

    public double getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(double depositNum) {
        this.depositNum = depositNum;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public int getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(int productSpecID) {
        this.productSpecID = productSpecID;
    }

    public double getReplenishmentNum() {
        return replenishmentNum;
    }

    public void setReplenishmentNum(double replenishmentNum) {
        this.replenishmentNum = replenishmentNum;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public double getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public double getRawProductNum() {
        return rawProductNum;
    }

    public void setRawProductNum(double rawProductNum) {
        this.rawProductNum = rawProductNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.depositNum);
        dest.writeInt(this.detailID);
        dest.writeInt(this.productID);
        dest.writeString(this.productName);
        dest.writeDouble(this.productNum);
        dest.writeDouble(this.productPrice);
        dest.writeString(this.productSpec);
        dest.writeInt(this.productSpecID);
        dest.writeDouble(this.replenishmentNum);
        dest.writeString(this.saleUnitName);
        dest.writeDouble(this.subtotalAmount);
    }

    public OrderDepositBean() {
    }

    protected OrderDepositBean(Parcel in) {
        this.depositNum = in.readDouble();
        this.detailID = in.readInt();
        this.productID = in.readInt();
        this.productName = in.readString();
        this.productNum = in.readDouble();
        this.productPrice = in.readDouble();
        this.productSpec = in.readString();
        this.productSpecID = in.readInt();
        this.replenishmentNum = in.readDouble();
        this.saleUnitName = in.readString();
        this.subtotalAmount = in.readDouble();
    }

    public static final Parcelable.Creator<OrderDepositBean> CREATOR = new Parcelable.Creator<OrderDepositBean>() {
        @Override
        public OrderDepositBean createFromParcel(Parcel source) {
            return new OrderDepositBean(source);
        }

        @Override
        public OrderDepositBean[] newArray(int size) {
            return new OrderDepositBean[size];
        }
    };
}
