package com.hll_sc_app.bean.goodsdemand;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class SpecialDemandBean implements Parcelable {
    private String actionTime;
    private String actionBy;
    private String createTime;
    private List<GoodsDemandItem> demandList;
    private String productCode;
    private String productID;
    private String productImgUrl;
    private String productName;
    private String purchaserID;
    private String purchaserName;

    protected SpecialDemandBean(Parcel in) {
        actionTime = in.readString();
        actionBy = in.readString();
        createTime = in.readString();
        demandList = in.createTypedArrayList(GoodsDemandItem.CREATOR);
        productCode = in.readString();
        productID = in.readString();
        productImgUrl = in.readString();
        productName = in.readString();
        purchaserID = in.readString();
        purchaserName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actionTime);
        dest.writeString(actionBy);
        dest.writeString(createTime);
        dest.writeTypedList(demandList);
        dest.writeString(productCode);
        dest.writeString(productID);
        dest.writeString(productImgUrl);
        dest.writeString(productName);
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpecialDemandBean> CREATOR = new Creator<SpecialDemandBean>() {
        @Override
        public SpecialDemandBean createFromParcel(Parcel in) {
            return new SpecialDemandBean(in);
        }

        @Override
        public SpecialDemandBean[] newArray(int size) {
            return new SpecialDemandBean[size];
        }
    };

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
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

    public List<GoodsDemandItem> getDemandList() {
        return demandList;
    }

    public void setDemandList(List<GoodsDemandItem> demandList) {
        this.demandList = demandList;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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
}
