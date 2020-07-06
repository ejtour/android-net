package com.hll_sc_app.bean.order.statistic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticShopBean implements Parcelable {
    private String linkman;
    private String mobile;
    private String purchaserName;
    private String shopID;
    private String shopName;
    private double totalAmount;
    private int totalNum;

    protected OrderStatisticShopBean(Parcel in) {
        linkman = in.readString();
        mobile = in.readString();
        purchaserName = in.readString();
        shopID = in.readString();
        shopName = in.readString();
        totalAmount = in.readDouble();
        totalNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(linkman);
        dest.writeString(mobile);
        dest.writeString(purchaserName);
        dest.writeString(shopID);
        dest.writeString(shopName);
        dest.writeDouble(totalAmount);
        dest.writeInt(totalNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderStatisticShopBean> CREATOR = new Creator<OrderStatisticShopBean>() {
        @Override
        public OrderStatisticShopBean createFromParcel(Parcel in) {
            return new OrderStatisticShopBean(in);
        }

        @Override
        public OrderStatisticShopBean[] newArray(int size) {
            return new OrderStatisticShopBean[size];
        }
    };

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
