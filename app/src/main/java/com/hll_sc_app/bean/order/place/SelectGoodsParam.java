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
    private String purchaserShopID;
    private String subID;
    private String threeID;
    private String searchWords;

    public SelectGoodsParam() {
    }

    protected SelectGoodsParam(Parcel in) {
        this.purchaserID = in.readString();
        this.purchaserShopID = in.readString();
        this.subID = in.readString();
        this.threeID = in.readString();
        this.searchWords = in.readString();
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserShopID);
        dest.writeString(this.subID);
        dest.writeString(this.threeID);
        dest.writeString(this.searchWords);
    }
}
