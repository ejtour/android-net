package com.hll_sc_app.bean.cooperation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * CooperationSourceBean
 *
 * @author zhuyingsong
 * @date 2019-07-17
 */
public class CooperationSourceBean implements Parcelable {
    private String actionTime;
    private String purchaserID;
    private String ownerName;
    private String createTime;
    private String agreeTime;
    private String groupID;
    private String action;
    private String id;
    private String shopID;
    private String source;
    private String ownerID;
    private String status;

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static final Parcelable.Creator<CooperationSourceBean> CREATOR =
        new Parcelable.Creator<CooperationSourceBean>() {
        @Override
        public CooperationSourceBean createFromParcel(Parcel source) {
            return new CooperationSourceBean(source);
        }

        @Override
        public CooperationSourceBean[] newArray(int size) {
            return new CooperationSourceBean[size];
        }
    };

    public CooperationSourceBean() {
    }

    protected CooperationSourceBean(Parcel in) {
        this.actionTime = in.readString();
        this.purchaserID = in.readString();
        this.ownerName = in.readString();
        this.createTime = in.readString();
        this.agreeTime = in.readString();
        this.groupID = in.readString();
        this.action = in.readString();
        this.id = in.readString();
        this.shopID = in.readString();
        this.source = in.readString();
        this.ownerID = in.readString();
        this.status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.purchaserID);
        dest.writeString(this.ownerName);
        dest.writeString(this.createTime);
        dest.writeString(this.agreeTime);
        dest.writeString(this.groupID);
        dest.writeString(this.action);
        dest.writeString(this.id);
        dest.writeString(this.shopID);
        dest.writeString(this.source);
        dest.writeString(this.ownerID);
        dest.writeString(this.status);
    }
}
