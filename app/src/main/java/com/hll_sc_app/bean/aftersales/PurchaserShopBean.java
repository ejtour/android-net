package com.hll_sc_app.bean.aftersales;

import android.os.Parcel;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserShopBean extends PurchaserBean {
    private String shopID;
    private String shopName;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.shopID);
        dest.writeString(this.shopName);
    }

    public PurchaserShopBean() {
    }

    protected PurchaserShopBean(Parcel in) {
        super(in);
        this.shopID = in.readString();
        this.shopName = in.readString();
    }

    public static final Creator<PurchaserShopBean> CREATOR = new Creator<PurchaserShopBean>() {
        @Override
        public PurchaserShopBean createFromParcel(Parcel source) {
            return new PurchaserShopBean(source);
        }

        @Override
        public PurchaserShopBean[] newArray(int size) {
            return new PurchaserShopBean[size];
        }
    };
}
