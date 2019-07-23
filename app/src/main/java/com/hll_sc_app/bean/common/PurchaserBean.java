package com.hll_sc_app.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserBean implements Parcelable {
    private String odmId;
    private String purchaserID;
    private String purchaserName;
    private boolean isSelected;
    private List<PurchaserShopBean> shopList;

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
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

    public List<PurchaserShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<PurchaserShopBean> shopList) {
        this.shopList = shopList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.odmId);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeList(this.shopList);
    }

    public PurchaserBean() {
    }

    protected PurchaserBean(Parcel in) {
        this.odmId = in.readString();
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.isSelected = in.readByte() != 0;
        this.shopList = new ArrayList<PurchaserShopBean>();
        in.readList(this.shopList, PurchaserShopBean.class.getClassLoader());
    }

    public static final Creator<PurchaserBean> CREATOR = new Creator<PurchaserBean>() {
        @Override
        public PurchaserBean createFromParcel(Parcel source) {
            return new PurchaserBean(source);
        }

        @Override
        public PurchaserBean[] newArray(int size) {
            return new PurchaserBean[size];
        }
    };

    public PurchaserBean deepCopy() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }
}
