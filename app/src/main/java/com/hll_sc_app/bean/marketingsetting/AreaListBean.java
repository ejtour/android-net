package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;

public class AreaListBean implements Parcelable {
    private String actionTime;
    private String createBy;
    private String cityName;
    private String actionBy;
    private String createTime;
    private String cityCode;
    private String provinceCode;
    private String odmId;
    private int action;
    private String id;
    private String provinceName;
    private String discountID;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.createBy);
        dest.writeString(this.cityName);
        dest.writeString(this.actionBy);
        dest.writeString(this.createTime);
        dest.writeString(this.cityCode);
        dest.writeString(this.provinceCode);
        dest.writeString(this.odmId);
        dest.writeInt(this.action);
        dest.writeString(this.id);
        dest.writeString(this.provinceName);
        dest.writeString(this.discountID);
    }

    public AreaListBean() {
    }

    protected AreaListBean(Parcel in) {
        this.actionTime = in.readString();
        this.createBy = in.readString();
        this.cityName = in.readString();
        this.actionBy = in.readString();
        this.createTime = in.readString();
        this.cityCode = in.readString();
        this.provinceCode = in.readString();
        this.odmId = in.readString();
        this.action = in.readInt();
        this.id = in.readString();
        this.provinceName = in.readString();
        this.discountID = in.readString();
    }

    public static final Parcelable.Creator<AreaListBean> CREATOR = new Parcelable.Creator<AreaListBean>() {
        @Override
        public AreaListBean createFromParcel(Parcel source) {
            return new AreaListBean(source);
        }

        @Override
        public AreaListBean[] newArray(int size) {
            return new AreaListBean[size];
        }
    };
}
