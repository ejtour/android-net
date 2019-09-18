package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SettlementInfoReq implements Parcelable {
    public static final Parcelable.Creator<SettlementInfoReq> CREATOR = new Parcelable.Creator<SettlementInfoReq>() {
        @Override
        public SettlementInfoReq createFromParcel(Parcel source) {
            return new SettlementInfoReq(source);
        }

        @Override
        public SettlementInfoReq[] newArray(int size) {
            return new SettlementInfoReq[size];
        }
    };
    private String purchaserID;
    private String purchaserName;
    private String shopID;
    private String shopName;
    private List<PlaceOrderSpecBean> specs;

    public SettlementInfoReq() {
    }

    protected SettlementInfoReq(Parcel in) {
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.shopID = in.readString();
        this.shopName = in.readString();
        this.specs = in.createTypedArrayList(PlaceOrderSpecBean.CREATOR);
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

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<PlaceOrderSpecBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<PlaceOrderSpecBean> specs) {
        this.specs = specs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.shopID);
        dest.writeString(this.shopName);
        dest.writeTypedList(this.specs);
    }
}
