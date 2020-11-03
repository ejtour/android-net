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
    private String shopArea;
    private String mobile;
    private String shopName;
    private String id;
    private String isActive;
    private String shopAddress;
    private String linkman;
    private String logoUrl;
    private String purchaserId;
    private String warehouseID;
    private transient boolean isSelect;
    private String isWarehouse;

    public WarehouseShopBean() {
    }

    protected WarehouseShopBean(Parcel in) {
        shopArea = in.readString();
        mobile = in.readString();
        shopName = in.readString();
        id = in.readString();
        isActive = in.readString();
        shopAddress = in.readString();
        linkman = in.readString();
        logoUrl = in.readString();
        purchaserId = in.readString();
        warehouseID = in.readString();
        isWarehouse = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopArea);
        dest.writeString(mobile);
        dest.writeString(shopName);
        dest.writeString(id);
        dest.writeString(isActive);
        dest.writeString(shopAddress);
        dest.writeString(linkman);
        dest.writeString(logoUrl);
        dest.writeString(purchaserId);
        dest.writeString(warehouseID);
        dest.writeString(isWarehouse);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WarehouseShopBean> CREATOR = new Creator<WarehouseShopBean>() {
        @Override
        public WarehouseShopBean createFromParcel(Parcel in) {
            return new WarehouseShopBean(in);
        }

        @Override
        public WarehouseShopBean[] newArray(int size) {
            return new WarehouseShopBean[size];
        }
    };

    public String getIsWarehouse() {
        return isWarehouse;
    }

    public void setIsWarehouse(String isWarehouse) {
        this.isWarehouse = isWarehouse;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
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

}
