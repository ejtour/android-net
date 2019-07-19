package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购商门店
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class PurchaserShopBean implements Parcelable {
    private String shopCode;
    private String shopArea;
    private String addressGaoDe;
    private String imagePath;
    private String mobile;
    private String shopName;
    private String source;
    private String isActive;
    private String shopAddress;
    private String linkman;
    private String purchaserID;
    private String latGaoDe;
    private String shopAdmin;
    private String lonGaoDe;
    private String shopPhone;
    private String shopID;
    private boolean select;
    private List<TimeBean> time;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TimeBean> getTime() {
        return time;
    }

    public void setTime(List<TimeBean> time) {
        this.time = time;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

    public String getAddressGaoDe() {
        return addressGaoDe;
    }

    public void setAddressGaoDe(String addressGaoDe) {
        this.addressGaoDe = addressGaoDe;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getLatGaoDe() {
        return latGaoDe;
    }

    public void setLatGaoDe(String latGaoDe) {
        this.latGaoDe = latGaoDe;
    }

    public String getShopAdmin() {
        return shopAdmin;
    }

    public void setShopAdmin(String shopAdmin) {
        this.shopAdmin = shopAdmin;
    }

    public String getLonGaoDe() {
        return lonGaoDe;
    }

    public void setLonGaoDe(String lonGaoDe) {
        this.lonGaoDe = lonGaoDe;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public static final Parcelable.Creator<PurchaserShopBean> CREATOR = new Parcelable.Creator<PurchaserShopBean>() {
        @Override
        public PurchaserShopBean createFromParcel(Parcel source) {
            return new PurchaserShopBean(source);
        }

        @Override
        public PurchaserShopBean[] newArray(int size) {
            return new PurchaserShopBean[size];
        }
    };

    public PurchaserShopBean() {
    }

    protected PurchaserShopBean(Parcel in) {
        this.shopCode = in.readString();
        this.shopArea = in.readString();
        this.addressGaoDe = in.readString();
        this.imagePath = in.readString();
        this.mobile = in.readString();
        this.shopName = in.readString();
        this.source = in.readString();
        this.isActive = in.readString();
        this.shopAddress = in.readString();
        this.linkman = in.readString();
        this.purchaserID = in.readString();
        this.latGaoDe = in.readString();
        this.shopAdmin = in.readString();
        this.lonGaoDe = in.readString();
        this.shopPhone = in.readString();
        this.shopID = in.readString();
        this.select = in.readByte() != 0;
        this.time = new ArrayList<TimeBean>();
        in.readList(this.time, TimeBean.class.getClassLoader());
        this.status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopCode);
        dest.writeString(this.shopArea);
        dest.writeString(this.addressGaoDe);
        dest.writeString(this.imagePath);
        dest.writeString(this.mobile);
        dest.writeString(this.shopName);
        dest.writeString(this.source);
        dest.writeString(this.isActive);
        dest.writeString(this.shopAddress);
        dest.writeString(this.linkman);
        dest.writeString(this.purchaserID);
        dest.writeString(this.latGaoDe);
        dest.writeString(this.shopAdmin);
        dest.writeString(this.lonGaoDe);
        dest.writeString(this.shopPhone);
        dest.writeString(this.shopID);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeList(this.time);
        dest.writeString(this.status);
    }
}
