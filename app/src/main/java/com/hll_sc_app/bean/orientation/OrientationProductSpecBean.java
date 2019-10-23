package com.hll_sc_app.bean.orientation;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.goods.SpecsBean;

import java.math.BigDecimal;
import java.util.Arrays;

public class OrientationProductSpecBean implements Parcelable {


    private BigDecimal productPrice;
    private String saleUnitID;
    private String saleUnitName;
    private String specContent;
    private String specID;
    /**
     * 是否设置定向售卖(0-未设置，1-设置)
     */
    private int appointSellType;

    public OrientationProductSpecBean() {
    }

    protected OrientationProductSpecBean(Parcel in) {
        this.productPrice = (BigDecimal) in.readSerializable();
        this.saleUnitID = in.readString();
        this.saleUnitName = in.readString();
        this.specContent = in.readString();
        this.specID = in.readString();
        this.appointSellType = in.readInt();
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

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

    public int getAppointSellType() {
        return appointSellType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setAppointSellType(int appointSellType) {
        this.appointSellType = appointSellType;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.productPrice);
        dest.writeString(this.saleUnitID);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.specContent);
        dest.writeString(this.specID);
        dest.writeInt(this.appointSellType);
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
