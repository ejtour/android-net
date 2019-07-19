package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

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
    private String purchaserName;

    private String shopProvince;
    private String shopDistrict;
    private String shopCity;
    private boolean select;
    private List<TimeBean> time;
    private String status;
    private String settlementWay;
    public static final Creator<PurchaserShopBean> CREATOR = new Creator<PurchaserShopBean>() {
        @Override
        public PurchaserShopBean createFromParcel(Parcel source) {
            return new PurchaserShopBean(source);
        }

        @Override
        public PurchaserShopBean[] newArray(int size) {
            return new PurchaserShopBean[size];
        }
    };
    private String accountPeriodType;
    private String accountPeriod;
    private String salesRepresentativeName;
    private String settleDate;
    private String driverName;
    private String salesRepresentativeID;
    private String deliveryWay;
    private String agreeTime;
    private String deliveryPeriod;
    private String driverID;

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
        this.purchaserName = in.readString();
        this.shopProvince = in.readString();
        this.shopDistrict = in.readString();
        this.shopCity = in.readString();
        this.select = in.readByte() != 0;
        this.time = in.createTypedArrayList(TimeBean.CREATOR);
        this.status = in.readString();
        this.settlementWay = in.readString();
        this.accountPeriodType = in.readString();
        this.accountPeriod = in.readString();
        this.settleDate = in.readString();
        this.salesRepresentativeName = in.readString();
        this.salesRepresentativeID = in.readString();
        this.driverName = in.readString();
        this.driverID = in.readString();
        this.deliveryWay = in.readString();
        this.agreeTime = in.readString();
        this.deliveryPeriod = in.readString();
    }

    public String getAccountPeriodType() {
        return accountPeriodType;
    }

    public void setAccountPeriodType(String accountPeriodType) {
        this.accountPeriodType = accountPeriodType;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getSalesRepresentativeName() {
        return salesRepresentativeName;
    }

    public void setSalesRepresentativeName(String salesRepresentativeName) {
        this.salesRepresentativeName = salesRepresentativeName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public String getShopProvince() {
        return shopProvince;
    }

    public void setShopProvince(String shopProvince) {
        this.shopProvince = shopProvince;
    }

    public String getShopDistrict() {
        return shopDistrict;
    }

    public void setShopDistrict(String shopDistrict) {
        this.shopDistrict = shopDistrict;
    }

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

    public PurchaserShopBean() {
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getSalesRepresentativeID() {
        return salesRepresentativeID;
    }

    public void setSalesRepresentativeID(String salesRepresentativeID) {
        this.salesRepresentativeID = salesRepresentativeID;
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
        dest.writeString(this.purchaserName);
        dest.writeString(this.shopProvince);
        dest.writeString(this.shopDistrict);
        dest.writeString(this.shopCity);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.time);
        dest.writeString(this.status);
        dest.writeString(this.settlementWay);
        dest.writeString(this.accountPeriodType);
        dest.writeString(this.accountPeriod);
        dest.writeString(this.settleDate);
        dest.writeString(this.salesRepresentativeName);
        dest.writeString(this.salesRepresentativeID);
        dest.writeString(this.driverName);
        dest.writeString(this.driverID);
        dest.writeString(this.deliveryWay);
        dest.writeString(this.agreeTime);
        dest.writeString(this.deliveryPeriod);
    }
}
