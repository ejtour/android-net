package com.hll_sc_app.bean.orientation;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class OrientationProductSpecBean implements Parcelable {


    private BigDecimal productPrice;
    private String saleUnitID;
    private String saleUnitName;
    private String specContent;

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public String getSaleUnitID() {
        return saleUnitID;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public void setSaleUnitID(String saleUnitID) {
        this.saleUnitID = saleUnitID;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.productPrice);
        dest.writeString(this.saleUnitID);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.specContent);
    }

    public OrientationProductSpecBean() {
    }

    protected OrientationProductSpecBean(Parcel in) {
        this.productPrice = (BigDecimal) in.readSerializable();
        this.saleUnitID = in.readString();
        this.saleUnitName = in.readString();
        this.specContent = in.readString();
    }

    public static final Creator<OrientationProductSpecBean> CREATOR = new Creator<OrientationProductSpecBean>() {
        @Override
        public OrientationProductSpecBean createFromParcel(Parcel source) {
            return new OrientationProductSpecBean(source);
        }

        @Override
        public OrientationProductSpecBean[] newArray(int size) {
            return new OrientationProductSpecBean[size];
        }
    };
}
