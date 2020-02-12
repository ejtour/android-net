package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SelectGoodsParam implements Parcelable {
    public static final Parcelable.Creator<SelectGoodsParam> CREATOR = new Parcelable.Creator<SelectGoodsParam>() {
        @Override
        public SelectGoodsParam createFromParcel(Parcel source) {
            return new SelectGoodsParam(source);
        }

        @Override
        public SelectGoodsParam[] newArray(int size) {
            return new SelectGoodsParam[size];
        }
    };
    private String purchaserID;
    private String shopID;
    private String shopName;
    private String subID;
    private String threeID;
    private boolean isWarehouse;
    private int warehouse;
    private String searchWords;

    public SelectGoodsParam() {
    }

    protected SelectGoodsParam(Parcel in) {
        this.purchaserID = in.readString();
        this.shopID = in.readString();
        this.shopName = in.readString();
        this.subID = in.readString();
        this.threeID = in.readString();
        this.isWarehouse = in.readByte() != 0;
        this.warehouse = in.readInt();
        this.searchWords = in.readString();
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSubID() {
        return subID;
    }

    public void setSubID(String subID) {
        this.subID = subID;
    }

    public String getThreeID() {
        return threeID;
    }

    public void setThreeID(String threeID) {
        this.threeID = threeID;
    }

    public boolean isWarehouse() {
        return isWarehouse;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(boolean warehouse) {
        isWarehouse = warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.purchaserID);
        dest.writeString(this.shopID);
        dest.writeString(this.shopName);
        dest.writeString(this.subID);
        dest.writeString(this.threeID);
        dest.writeByte((byte) (isWarehouse ? 1 : 0));
        dest.writeInt(warehouse);
        dest.writeString(this.searchWords);
    }
}
