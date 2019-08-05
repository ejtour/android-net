package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 起送金额
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class DeliveryMinimumBean implements Parcelable {
    /**
     * 设置类型 0-地区 1-采购商
     */
    private String settings;
    private String supplyName;
    private String actionTime;
    private String createBy;
    private String sendPrice;
    private String actionBy;
    private String createTime;
    private String supplyShopID;
    private String supplyID;
    private String divideName;
    private String areaNum;
    private String sendAmountID;

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(String sendPrice) {
        this.sendPrice = sendPrice;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public String getDivideName() {
        return divideName;
    }

    public void setDivideName(String divideName) {
        this.divideName = divideName;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getSendAmountID() {
        return sendAmountID;
    }

    public void setSendAmountID(String sendAmountID) {
        this.sendAmountID = sendAmountID;
    }

    public static final Parcelable.Creator<DeliveryMinimumBean> CREATOR =
        new Parcelable.Creator<DeliveryMinimumBean>() {
        @Override
        public DeliveryMinimumBean createFromParcel(Parcel source) {
            return new DeliveryMinimumBean(source);
        }

        @Override
        public DeliveryMinimumBean[] newArray(int size) {
            return new DeliveryMinimumBean[size];
        }
    };

    public DeliveryMinimumBean() {
    }

    protected DeliveryMinimumBean(Parcel in) {
        this.settings = in.readString();
        this.supplyName = in.readString();
        this.actionTime = in.readString();
        this.createBy = in.readString();
        this.sendPrice = in.readString();
        this.actionBy = in.readString();
        this.createTime = in.readString();
        this.supplyShopID = in.readString();
        this.supplyID = in.readString();
        this.divideName = in.readString();
        this.areaNum = in.readString();
        this.sendAmountID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.settings);
        dest.writeString(this.supplyName);
        dest.writeString(this.actionTime);
        dest.writeString(this.createBy);
        dest.writeString(this.sendPrice);
        dest.writeString(this.actionBy);
        dest.writeString(this.createTime);
        dest.writeString(this.supplyShopID);
        dest.writeString(this.supplyID);
        dest.writeString(this.divideName);
        dest.writeString(this.areaNum);
        dest.writeString(this.sendAmountID);
    }
}
