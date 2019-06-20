package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 规格的 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class SupplierShopsBean implements Parcelable {
    public static final Parcelable.Creator<SupplierShopsBean> CREATOR = new Parcelable.Creator<SupplierShopsBean>() {
        @Override
        public SupplierShopsBean createFromParcel(Parcel source) {
            return new SupplierShopsBean(source);
        }

        @Override
        public SupplierShopsBean[] newArray(int size) {
            return new SupplierShopsBean[size];
        }
    };
    private String shopID;
    private String isActive;

    public SupplierShopsBean() {
    }

    protected SupplierShopsBean(Parcel in) {
        this.shopID = in.readString();
        this.isActive = in.readString();
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopID);
        dest.writeString(this.isActive);
    }
}
