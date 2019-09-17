package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class PlaceOrderSpecBean implements Parcelable {
    private String productID;
    private String productSpecID;
    private String productNum;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(String productSpecID) {
        this.productSpecID = productSpecID;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productID);
        dest.writeString(this.productSpecID);
        dest.writeString(this.productNum);
    }

    public PlaceOrderSpecBean() {
    }

    protected PlaceOrderSpecBean(Parcel in) {
        this.productID = in.readString();
        this.productSpecID = in.readString();
        this.productNum = in.readString();
    }

    public static final Parcelable.Creator<PlaceOrderSpecBean> CREATOR = new Parcelable.Creator<PlaceOrderSpecBean>() {
        @Override
        public PlaceOrderSpecBean createFromParcel(Parcel source) {
            return new PlaceOrderSpecBean(source);
        }

        @Override
        public PlaceOrderSpecBean[] newArray(int size) {
            return new PlaceOrderSpecBean[size];
        }
    };
}
