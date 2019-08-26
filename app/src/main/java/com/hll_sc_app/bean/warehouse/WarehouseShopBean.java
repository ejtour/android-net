package com.hll_sc_app.bean.warehouse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 代仓门店详情
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class WarehouseShopBean implements Parcelable {
    public static final Creator<WarehouseShopBean> CREATOR = new Creator<WarehouseShopBean>() {
        @Override
        public WarehouseShopBean createFromParcel(Parcel source) {
            return new WarehouseShopBean(source);
        }

        @Override
        public WarehouseShopBean[] newArray(int size) {
            return new WarehouseShopBean[size];
        }
    };
    private String shopArea;
    private String mobile;
    private String shopName;
    private String id;
    private String isActive;
    private String shopAddress;
    private String linkman;
    private String logoUrl;
    private String purchaserId;

    public WarehouseShopBean() {
    }

    protected WarehouseShopBean(Parcel in) {
        this.shopArea = in.readString();
        this.mobile = in.readString();
        this.shopName = in.readString();
        this.id = in.readString();
        this.isActive = in.readString();
        this.shopAddress = in.readString();
        this.linkman = in.readString();
        this.logoUrl = in.readString();
        this.purchaserId = in.readString();
    }

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopArea);
        dest.writeString(this.mobile);
        dest.writeString(this.shopName);
        dest.writeString(this.id);
        dest.writeString(this.isActive);
        dest.writeString(this.shopAddress);
        dest.writeString(this.linkman);
        dest.writeString(this.logoUrl);
        dest.writeString(this.purchaserId);
    }
}
