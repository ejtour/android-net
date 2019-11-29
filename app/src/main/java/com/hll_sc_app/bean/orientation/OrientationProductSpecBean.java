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
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    /**
     * 是否设置定向售卖(0-未设置，1-设置)
     */
    private int appointSellType;

    public OrientationProductSpecBean() {
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

    public void setAppointSellType(int appointSellType) {
        this.appointSellType = appointSellType;
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
        dest.writeString(this.specID);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.appointSellType);
    }

    protected OrientationProductSpecBean(Parcel in) {
        this.productPrice = (BigDecimal) in.readSerializable();
        this.saleUnitID = in.readString();
        this.saleUnitName = in.readString();
        this.specContent = in.readString();
        this.specID = in.readString();
        this.isSelect = in.readByte() != 0;
        this.appointSellType = in.readInt();
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
