package com.hll_sc_app.bean.orientation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrientationDetailBean implements Parcelable {

    private String imgUrl;
    private String productID;
    private String productName;
    private String saleSpecNum;
    private String supplierName;
    private ArrayList<OrientationProductSpecBean> specs;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSaleSpecNum(String saleSpecNum) {
        this.saleSpecNum = saleSpecNum;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSpecs(ArrayList<OrientationProductSpecBean> specs) {
        this.specs = specs;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getSaleSpecNum() {
        return saleSpecNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public ArrayList<OrientationProductSpecBean> getSpecs() {
        return specs;
    }

    public static Creator<OrientationDetailBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeString(this.productID);
        dest.writeString(this.productName);
        dest.writeString(this.saleSpecNum);
        dest.writeString(this.supplierName);
        dest.writeTypedList(this.specs);
    }

    public OrientationDetailBean() {
    }

    protected OrientationDetailBean(Parcel in) {
        this.imgUrl = in.readString();
        this.productID = in.readString();
        this.productName = in.readString();
        this.saleSpecNum = in.readString();
        this.supplierName = in.readString();
        this.specs = in.createTypedArrayList(OrientationProductSpecBean.CREATOR);
    }

    public static final Creator<OrientationDetailBean> CREATOR = new Creator<OrientationDetailBean>() {
        @Override
        public OrientationDetailBean createFromParcel(Parcel source) {
            return new OrientationDetailBean(source);
        }

        @Override
        public OrientationDetailBean[] newArray(int size) {
            return new OrientationDetailBean[size];
        }
    };
}
