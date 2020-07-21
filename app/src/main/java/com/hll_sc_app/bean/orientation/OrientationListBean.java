package com.hll_sc_app.bean.orientation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrientationListBean implements Parcelable {
    private String id;
    private String productNum;
    private String purchaserID;
    private String purchaserName;
    private String purchaserImgUrl;
    private Integer from;//0-来自新增 1-来自修改
    private List<OrientationDetailBean> productList;

    protected OrientationListBean(Parcel in) {
        id = in.readString();
        productNum = in.readString();
        purchaserID = in.readString();
        purchaserName = in.readString();
        purchaserImgUrl = in.readString();
        if (in.readByte() == 0) {
            from = null;
        } else {
            from = in.readInt();
        }
        productList = in.createTypedArrayList(OrientationDetailBean.CREATOR);
        purchaserShopIDs = in.readString();
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productNum);
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeString(purchaserImgUrl);
        if (from == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(from);
        }
        dest.writeTypedList(productList);
        dest.writeString(purchaserShopIDs);
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrientationListBean> CREATOR = new Creator<OrientationListBean>() {
        @Override
        public OrientationListBean createFromParcel(Parcel in) {
            return new OrientationListBean(in);
        }

        @Override
        public OrientationListBean[] newArray(int size) {
            return new OrientationListBean[size];
        }
    };

    public OrientationListBean deepCopy() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }

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

    public List<OrientationDetailBean> getProductList() {
        return productList;
    }

    public void setProductList(List<OrientationDetailBean> productList) {
        this.productList = productList;
    }

    public OrientationListBean() {
    }
}
