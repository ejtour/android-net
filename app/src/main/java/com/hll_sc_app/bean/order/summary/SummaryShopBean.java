package com.hll_sc_app.bean.order.summary;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class SummaryShopBean implements Parcelable {
    private String purchaserID;
    private String purchaserName;
    private String purchaserLogo;
    private double productAmount;
    private int productCount;
    private double productNum;
    private String shipperID;
    private String shopID;
    private String shopName;
    private String stallID;
    private String stallName;
    private List<SummaryShopBean> stallList;
    private List<SummaryProductBean> productList;

    protected SummaryShopBean(Parcel in) {
        purchaserID = in.readString();
        purchaserName = in.readString();
        purchaserLogo = in.readString();
        productAmount = in.readDouble();
        productCount = in.readInt();
        productNum = in.readDouble();
        shipperID = in.readString();
        shopID = in.readString();
        shopName = in.readString();
        stallID = in.readString();
        stallName = in.readString();
        stallList = in.createTypedArrayList(SummaryShopBean.CREATOR);
        productList = in.createTypedArrayList(SummaryProductBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeString(purchaserLogo);
        dest.writeDouble(productAmount);
        dest.writeInt(productCount);
        dest.writeDouble(productNum);
        dest.writeString(shipperID);
        dest.writeString(shopID);
        dest.writeString(shopName);
        dest.writeString(stallID);
        dest.writeString(stallName);
        dest.writeTypedList(stallList);
        dest.writeTypedList(productList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SummaryShopBean> CREATOR = new Creator<SummaryShopBean>() {
        @Override
        public SummaryShopBean createFromParcel(Parcel in) {
            return new SummaryShopBean(in);
        }

        @Override
        public SummaryShopBean[] newArray(int size) {
            return new SummaryShopBean[size];
        }
    };

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserLogo() {
        return purchaserLogo;
    }

    public void setPurchaserLogo(String purchaserLogo) {
        this.purchaserLogo = purchaserLogo;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStallID() {
        return stallID;
    }

    public void setStallID(String stallID) {
        this.stallID = stallID;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public List<SummaryShopBean> getStallList() {
        return stallList;
    }

    public void setStallList(List<SummaryShopBean> stallList) {
        this.stallList = stallList;
    }

    public List<SummaryProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<SummaryProductBean> productList) {
        this.productList = productList;
    }
}
