package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 配送公司
 *
 * @author zhuyingsong
 * @date 2019-07-27
 */
public class DeliveryCompanyBean implements Parcelable, Serializable {
    public static final Parcelable.Creator<DeliveryCompanyBean> CREATOR =
        new Parcelable.Creator<DeliveryCompanyBean>() {
            @Override
            public DeliveryCompanyBean createFromParcel(Parcel source) {
                return new DeliveryCompanyBean(source);
            }

            @Override
            public DeliveryCompanyBean[] newArray(int size) {
                return new DeliveryCompanyBean[size];
            }
        };
    private String actionTime;
    private String deliveryCompanyName;
    private String createTime;
    private String groupID;
    private String action;
    private String id;
    private String status;

    public DeliveryCompanyBean() {
    }

    protected DeliveryCompanyBean(Parcel in) {
        this.actionTime = in.readString();
        this.deliveryCompanyName = in.readString();
        this.createTime = in.readString();
        this.groupID = in.readString();
        this.action = in.readString();
        this.id = in.readString();
        this.status = in.readString();
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.deliveryCompanyName);
        dest.writeString(this.createTime);
        dest.writeString(this.groupID);
        dest.writeString(this.action);
        dest.writeString(this.id);
        dest.writeString(this.status);
    }
}
