package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 起送金额-采购商
 *
 * @author zhuyingsong
 * @date 2019-07-31
 */
public class DeliveryPurchaserBean implements Parcelable {
    private String purchaserID;
    private String purchaserName;
    private int purchaserShopNum;
    private List<String> purchaserShopList;

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

    public int getPurchaserShopNum() {
        return purchaserShopNum;
    }

    public void setPurchaserShopNum(int purchaserShopNum) {
        this.purchaserShopNum = purchaserShopNum;
    }

    public List<String> getPurchaserShopList() {
        return purchaserShopList;
    }

    public void setPurchaserShopList(List<String> purchaserShopList) {
        this.purchaserShopList = purchaserShopList;
    }

    public static final Parcelable.Creator<DeliveryPurchaserBean> CREATOR =
        new Parcelable.Creator<DeliveryPurchaserBean>() {
            @Override
            public DeliveryPurchaserBean createFromParcel(Parcel source) {
                return new DeliveryPurchaserBean(source);
            }

            @Override
            public DeliveryPurchaserBean[] newArray(int size) {
                return new DeliveryPurchaserBean[size];
            }
        };

    public DeliveryPurchaserBean() {
    }

    protected DeliveryPurchaserBean(Parcel in) {
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.purchaserShopNum = in.readInt();
        this.purchaserShopList = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeInt(this.purchaserShopNum);
        dest.writeStringList(this.purchaserShopList);
    }
}
