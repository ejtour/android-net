package com.hll_sc_app.bean.orientation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OrientationListBean implements Parcelable {
    private String id;
    private String productNum;
    private String purchaserID;
    private String purchaserName;
    private String purchaserImgUrl;
    private Integer from;//0-来自新增 1-来自修改

    public void setPurchaserImgUrl(String purchaserImgUrl) {
        this.purchaserImgUrl = purchaserImgUrl;
    }

    public String getPurchaserImgUrl() {
        return purchaserImgUrl;
    }

    private String purchaserShopIDs;
    private List<Long> purchaserShopIDList;
    private Integer type = 0;

    public void setId(String id) {
        this.id = id;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public void setPurchaserShopIDs(String purchaserShopIDs) {
        this.purchaserShopIDs = purchaserShopIDs;
    }

    public void setPurchaserShopIDList(List<Long> purchaserShopIDList) {
        this.purchaserShopIDList = purchaserShopIDList;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getProductNum() {
        return productNum;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public String getPurchaserShopIDs() {
        return purchaserShopIDs;
    }

    public List<Long> getPurchaserShopIDList() {
        return purchaserShopIDList;
    }

    public Integer getType() {
        return type;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.productNum);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.purchaserImgUrl);
        dest.writeValue(this.from);
        dest.writeString(this.purchaserShopIDs);
        dest.writeList(this.purchaserShopIDList);
        dest.writeValue(this.type);
    }

    public OrientationListBean() {
    }

    protected OrientationListBean(Parcel in) {
        this.id = in.readString();
        this.productNum = in.readString();
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.purchaserImgUrl = in.readString();
        this.from = (Integer) in.readValue(Integer.class.getClassLoader());
        this.purchaserShopIDs = in.readString();
        this.purchaserShopIDList = new ArrayList<Long>();
        in.readList(this.purchaserShopIDList, Long.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<OrientationListBean> CREATOR = new Creator<OrientationListBean>() {
        @Override
        public OrientationListBean createFromParcel(Parcel source) {
            return new OrientationListBean(source);
        }

        @Override
        public OrientationListBean[] newArray(int size) {
            return new OrientationListBean[size];
        }
    };
}
