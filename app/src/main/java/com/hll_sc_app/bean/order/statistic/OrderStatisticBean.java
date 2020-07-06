package com.hll_sc_app.bean.order.statistic;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticBean implements Parcelable {
    private int coopShopNum;
    private String purchaserName;
    private String purchaserID;
    private int shopNum;
    private String groupLogoUrl;
    private boolean notOrder;
    private int timeType;
    private List<OrderStatisticShopBean> shopList;

    protected OrderStatisticBean(Parcel in) {
        coopShopNum = in.readInt();
        purchaserName = in.readString();
        purchaserID = in.readString();
        shopNum = in.readInt();
        groupLogoUrl = in.readString();
        notOrder = in.readByte() != 0;
        timeType = in.readInt();
        shopList = in.createTypedArrayList(OrderStatisticShopBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(coopShopNum);
        dest.writeString(purchaserName);
        dest.writeString(purchaserID);
        dest.writeInt(shopNum);
        dest.writeString(groupLogoUrl);
        dest.writeByte((byte) (notOrder ? 1 : 0));
        dest.writeInt(timeType);
        dest.writeTypedList(shopList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderStatisticBean> CREATOR = new Creator<OrderStatisticBean>() {
        @Override
        public OrderStatisticBean createFromParcel(Parcel in) {
            return new OrderStatisticBean(in);
        }

        @Override
        public OrderStatisticBean[] newArray(int size) {
            return new OrderStatisticBean[size];
        }
    };

    public String getGroupLogoUrl() {
        return groupLogoUrl;
    }

    public void setGroupLogoUrl(String groupLogoUrl) {
        this.groupLogoUrl = groupLogoUrl;
    }

    public int getCoopShopNum() {
        return coopShopNum;
    }

    public void setCoopShopNum(int coopShopNum) {
        this.coopShopNum = coopShopNum;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public boolean isNotOrder() {
        return notOrder;
    }

    public void setNotOrder(boolean notOrder) {
        this.notOrder = notOrder;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public List<OrderStatisticShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<OrderStatisticShopBean> shopList) {
        this.shopList = shopList;
    }
}
