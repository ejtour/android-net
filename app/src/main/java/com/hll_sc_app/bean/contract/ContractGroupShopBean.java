package com.hll_sc_app.bean.contract;

import android.os.Parcel;
import android.os.Parcelable;

public class ContractGroupShopBean implements Parcelable {
    private String purchaserID;
    private String purchaserName;
    private String shopID;
    private String shopName;
    private  int purchaserType =1; //采购商类型 1-合作关系，2-意向客户

    public ContractGroupShopBean() {
    }

    public boolean isCooperation(){
        return purchaserType ==1;
    }

    protected ContractGroupShopBean(Parcel in) {
        purchaserID = in.readString();
        purchaserName = in.readString();
        shopID = in.readString();
        shopName = in.readString();
        purchaserType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeString(shopID);
        dest.writeString(shopName);
        dest.writeInt(purchaserType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContractGroupShopBean> CREATOR = new Creator<ContractGroupShopBean>() {
        @Override
        public ContractGroupShopBean createFromParcel(Parcel in) {
            return new ContractGroupShopBean(in);
        }

        @Override
        public ContractGroupShopBean[] newArray(int size) {
            return new ContractGroupShopBean[size];
        }
    };

    public int getPurchaserType() {
        return purchaserType;
    }

    public void setPurchaserType(int purchaserType) {
        this.purchaserType = purchaserType;
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
}
