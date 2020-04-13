package com.hll_sc_app.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

public class WareHouseShipperBean implements Parcelable {

    private String   groupID;
    private String groupName;
    private String   id;
    private String   purchaserID;
    private String purchaserName;


    public WareHouseShipperBean() {
    }

    protected WareHouseShipperBean(Parcel in) {
        groupID = in.readString();
        groupName = in.readString();
        id = in.readString();
        purchaserID = in.readString();
        purchaserName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupID);
        dest.writeString(groupName);
        dest.writeString(id);
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WareHouseShipperBean> CREATOR = new Creator<WareHouseShipperBean>() {
        @Override
        public WareHouseShipperBean createFromParcel(Parcel in) {
            return new WareHouseShipperBean(in);
        }

        @Override
        public WareHouseShipperBean[] newArray(int size) {
            return new WareHouseShipperBean[size];
        }
    };

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
