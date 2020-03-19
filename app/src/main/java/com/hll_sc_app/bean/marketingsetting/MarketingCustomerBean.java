package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/3
 */
public class MarketingCustomerBean implements Parcelable {
    private String purchaserID;
    private String purchaserName;
    private int shopCount;
    private String shopName;
    private String shopID;
    private int type; // 1集团维度 0门店维度

    public MarketingCustomerBean() {
    }

    protected MarketingCustomerBean(Parcel in) {
        purchaserID = in.readString();
        purchaserName = in.readString();
        shopCount = in.readInt();
        shopName = in.readString();
        shopID = in.readString();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeInt(shopCount);
        dest.writeString(shopName);
        dest.writeString(shopID);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MarketingCustomerBean> CREATOR = new Creator<MarketingCustomerBean>() {
        @Override
        public MarketingCustomerBean createFromParcel(Parcel in) {
            return new MarketingCustomerBean(in);
        }

        @Override
        public MarketingCustomerBean[] newArray(int size) {
            return new MarketingCustomerBean[size];
        }
    };

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

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
