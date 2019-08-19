package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

public class GiveBean implements Parcelable {


    private String giveCount;
    private String giveTargetID;
    private String giveTargetName;
    private int giveType;

    public String getGiveCount() {
        return giveCount;
    }

    public void setGiveCount(String giveCount) {
        this.giveCount = giveCount;
    }

    public String getGiveTargetID() {
        return giveTargetID;
    }

    public void setGiveTargetID(String giveTargetID) {
        this.giveTargetID = giveTargetID;
    }

    public String getGiveTargetName() {
        return giveTargetName;
    }

    public void setGiveTargetName(String giveTargetName) {
        this.giveTargetName = giveTargetName;
    }

    public int getGiveType() {
        return giveType;
    }

    public void setGiveType(int giveType) {
        this.giveType = giveType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.giveCount);
        dest.writeString(this.giveTargetID);
        dest.writeString(this.giveTargetName);
        dest.writeInt(this.giveType);
    }

    public GiveBean() {
    }

    protected GiveBean(Parcel in) {
        this.giveCount = in.readString();
        this.giveTargetID = in.readString();
        this.giveTargetName = in.readString();
        this.giveType = in.readInt();
    }

    public static final Creator<GiveBean> CREATOR = new Creator<GiveBean>() {
        @Override
        public GiveBean createFromParcel(Parcel source) {
            return new GiveBean(source);
        }

        @Override
        public GiveBean[] newArray(int size) {
            return new GiveBean[size];
        }
    };
}
