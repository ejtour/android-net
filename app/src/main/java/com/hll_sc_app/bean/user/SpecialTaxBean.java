package com.hll_sc_app.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/31
 */

public class SpecialTaxBean implements Parcelable {
    public static final Creator<SpecialTaxBean> CREATOR = new Creator<SpecialTaxBean>() {
        @Override
        public SpecialTaxBean createFromParcel(Parcel in) {
            return new SpecialTaxBean(in);
        }

        @Override
        public SpecialTaxBean[] newArray(int size) {
            return new SpecialTaxBean[size];
        }
    };
    private String id;
    private String imgUrl;
    private String productID;
    private String productName;
    private int saleSpecNum;
    private String standardUnitName;
    private String taxRate;
    private boolean isSelected;

    public SpecialTaxBean() {
    }

    protected SpecialTaxBean(Parcel in) {
        id = in.readString();
        imgUrl = in.readString();
        productID = in.readString();
        productName = in.readString();
        saleSpecNum = in.readInt();
        standardUnitName = in.readString();
        taxRate = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imgUrl);
        dest.writeString(productID);
        dest.writeString(productName);
        dest.writeInt(saleSpecNum);
        dest.writeString(standardUnitName);
        dest.writeString(taxRate);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SpecialTaxBean copy(){
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSaleSpecNum() {
        return saleSpecNum;
    }

    public void setSaleSpecNum(int saleSpecNum) {
        this.saleSpecNum = saleSpecNum;
    }

    public String getStandardUnitName() {
        return standardUnitName;
    }

    public void setStandardUnitName(String standardUnitName) {
        this.standardUnitName = standardUnitName;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
