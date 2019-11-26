package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.cooperation.CooperationSourceBean;

import java.util.List;

/**
 * 采购商门店
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class PurchaserShopBean implements Parcelable {
    public static final Creator<PurchaserShopBean> CREATOR = new Creator<PurchaserShopBean>() {
        @Override
        public PurchaserShopBean createFromParcel(Parcel in) {
            return new PurchaserShopBean(in);
        }

        @Override
        public PurchaserShopBean[] newArray(int size) {
            return new PurchaserShopBean[size];
        }
    };
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
    private String salesmanName;
    private String salesmanPhone;
    private int cooperationActive;
    private int fallFlag;
    private int newFlag;
    private double actualSevenAvgBillNum;
    private double currentAvgAmount;
    private int currentMonthBillNum;
    private int currentWeekBillNum;
    private String lastBillTime;
    private String lastVisitTime;
    private double monthScale;
    private int preMonthBillNum;
    private int preWeekBillNum;
    private int returnBillNum;
    private double sevenAvgBillNum;
    private double sevenBillAmount;
    private int sevenBillNum;
    private double sevenDayAvgAmount;
    private double thirtyBillAmount;
    private int thirtyBillNum;
    private double thirtyDayAvgAmount;
    private double thirtyDayAvgBillNum;
    private double todayBillAmount;
    private int todayBillNum;
    private double unsettledBillAmount;
    private int unsettledBillNum;
    private double weekScale;
    private String businessStartTime;
    private String businessEndTime;
    private String cooperationTime;
    private List<CooperationSourceBean> cooperationSource;

    public PurchaserShopBean() {
    }

    protected PurchaserShopBean(Parcel in) {
        shopCode = in.readString();
        shopArea = in.readString();
        addressGaoDe = in.readString();
        imagePath = in.readString();
        mobile = in.readString();
        shopName = in.readString();
        source = in.readString();
        isActive = in.readString();
        shopAddress = in.readString();
        linkman = in.readString();
        purchaserID = in.readString();
        latGaoDe = in.readString();
        shopAdmin = in.readString();
        lonGaoDe = in.readString();
        shopPhone = in.readString();
        shopID = in.readString();
        purchaserName = in.readString();
        shopProvince = in.readString();
        shopDistrict = in.readString();
        shopCity = in.readString();
        select = in.readByte() != 0;
        time = in.createTypedArrayList(TimeBean.CREATOR);
        status = in.readString();
        settlementWay = in.readString();
        accountPeriodType = in.readString();
        accountPeriod = in.readString();
        salesRepresentativeName = in.readString();
        settleDate = in.readString();
        driverName = in.readString();
        salesRepresentativeID = in.readString();
        deliveryWay = in.readString();
        agreeTime = in.readString();
        deliveryPeriod = in.readString();
        driverID = in.readString();
        salesmanName = in.readString();
        salesmanPhone = in.readString();
        cooperationActive = in.readInt();
        fallFlag = in.readInt();
        newFlag = in.readInt();
        actualSevenAvgBillNum = in.readDouble();
        currentAvgAmount = in.readDouble();
        currentMonthBillNum = in.readInt();
        currentWeekBillNum = in.readInt();
        lastBillTime = in.readString();
        lastVisitTime = in.readString();
        monthScale = in.readDouble();
        preMonthBillNum = in.readInt();
        preWeekBillNum = in.readInt();
        returnBillNum = in.readInt();
        sevenAvgBillNum = in.readDouble();
        sevenBillAmount = in.readDouble();
        sevenBillNum = in.readInt();
        sevenDayAvgAmount = in.readDouble();
        thirtyBillAmount = in.readDouble();
        thirtyBillNum = in.readInt();
        thirtyDayAvgAmount = in.readDouble();
        thirtyDayAvgBillNum = in.readDouble();
        todayBillAmount = in.readDouble();
        todayBillNum = in.readInt();
        unsettledBillAmount = in.readDouble();
        unsettledBillNum = in.readInt();
        weekScale = in.readDouble();
        businessStartTime = in.readString();
        businessEndTime = in.readString();
        cooperationTime = in.readString();
        cooperationSource = in.createTypedArrayList(CooperationSourceBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopCode);
        dest.writeString(shopArea);
        dest.writeString(addressGaoDe);
        dest.writeString(imagePath);
        dest.writeString(mobile);
        dest.writeString(shopName);
        dest.writeString(source);
        dest.writeString(isActive);
        dest.writeString(shopAddress);
        dest.writeString(linkman);
        dest.writeString(purchaserID);
        dest.writeString(latGaoDe);
        dest.writeString(shopAdmin);
        dest.writeString(lonGaoDe);
        dest.writeString(shopPhone);
        dest.writeString(shopID);
        dest.writeString(purchaserName);
        dest.writeString(shopProvince);
        dest.writeString(shopDistrict);
        dest.writeString(shopCity);
        dest.writeByte((byte) (select ? 1 : 0));
        dest.writeTypedList(time);
        dest.writeString(status);
        dest.writeString(settlementWay);
        dest.writeString(accountPeriodType);
        dest.writeString(accountPeriod);
        dest.writeString(salesRepresentativeName);
        dest.writeString(settleDate);
        dest.writeString(driverName);
        dest.writeString(salesRepresentativeID);
        dest.writeString(deliveryWay);
        dest.writeString(agreeTime);
        dest.writeString(deliveryPeriod);
        dest.writeString(driverID);
        dest.writeString(salesmanName);
        dest.writeString(salesmanPhone);
        dest.writeInt(cooperationActive);
        dest.writeInt(fallFlag);
        dest.writeInt(newFlag);
        dest.writeDouble(actualSevenAvgBillNum);
        dest.writeDouble(currentAvgAmount);
        dest.writeInt(currentMonthBillNum);
        dest.writeInt(currentWeekBillNum);
        dest.writeString(lastBillTime);
        dest.writeString(lastVisitTime);
        dest.writeDouble(monthScale);
        dest.writeInt(preMonthBillNum);
        dest.writeInt(preWeekBillNum);
        dest.writeInt(returnBillNum);
        dest.writeDouble(sevenAvgBillNum);
        dest.writeDouble(sevenBillAmount);
        dest.writeInt(sevenBillNum);
        dest.writeDouble(sevenDayAvgAmount);
        dest.writeDouble(thirtyBillAmount);
        dest.writeInt(thirtyBillNum);
        dest.writeDouble(thirtyDayAvgAmount);
        dest.writeDouble(thirtyDayAvgBillNum);
        dest.writeDouble(todayBillAmount);
        dest.writeInt(todayBillNum);
        dest.writeDouble(unsettledBillAmount);
        dest.writeInt(unsettledBillNum);
        dest.writeDouble(weekScale);
        dest.writeString(businessStartTime);
        dest.writeString(businessEndTime);
        dest.writeString(cooperationTime);
        dest.writeTypedList(cooperationSource);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCooperationActive() {
        return cooperationActive;
    }

    public void setCooperationActive(int cooperationActive) {
        this.cooperationActive = cooperationActive;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanPhone() {
        return salesmanPhone;
    }

    public void setSalesmanPhone(String salesmanPhone) {
        this.salesmanPhone = salesmanPhone;
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

    public String getSalesRepresentativeID() {
        return salesRepresentativeID;
    }

    public void setSalesRepresentativeID(String salesRepresentativeID) {
        this.salesRepresentativeID = salesRepresentativeID;
    }

    public List<CooperationSourceBean> getCooperationSource() {
        return cooperationSource;
    }

    public void setCooperationSource(List<CooperationSourceBean> cooperationSource) {
        this.cooperationSource = cooperationSource;
    }

    public int getFallFlag() {
        return fallFlag;
    }

    public void setFallFlag(int fallFlag) {
        this.fallFlag = fallFlag;
    }

    public int getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(int newFlag) {
        this.newFlag = newFlag;
    }

    public double getActualSevenAvgBillNum() {
        return actualSevenAvgBillNum;
    }

    public void setActualSevenAvgBillNum(double actualSevenAvgBillNum) {
        this.actualSevenAvgBillNum = actualSevenAvgBillNum;
    }

    public double getCurrentAvgAmount() {
        return currentAvgAmount;
    }

    public void setCurrentAvgAmount(double currentAvgAmount) {
        this.currentAvgAmount = currentAvgAmount;
    }

    public int getCurrentMonthBillNum() {
        return currentMonthBillNum;
    }

    public void setCurrentMonthBillNum(int currentMonthBillNum) {
        this.currentMonthBillNum = currentMonthBillNum;
    }

    public int getCurrentWeekBillNum() {
        return currentWeekBillNum;
    }

    public void setCurrentWeekBillNum(int currentWeekBillNum) {
        this.currentWeekBillNum = currentWeekBillNum;
    }

    public String getLastBillTime() {
        return lastBillTime;
    }

    public void setLastBillTime(String lastBillTime) {
        this.lastBillTime = lastBillTime;
    }

    public String getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(String lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public double getMonthScale() {
        return monthScale;
    }

    public void setMonthScale(double monthScale) {
        this.monthScale = monthScale;
    }

    public int getPreMonthBillNum() {
        return preMonthBillNum;
    }

    public void setPreMonthBillNum(int preMonthBillNum) {
        this.preMonthBillNum = preMonthBillNum;
    }

    public int getPreWeekBillNum() {
        return preWeekBillNum;
    }

    public void setPreWeekBillNum(int preWeekBillNum) {
        this.preWeekBillNum = preWeekBillNum;
    }

    public int getReturnBillNum() {
        return returnBillNum;
    }

    public void setReturnBillNum(int returnBillNum) {
        this.returnBillNum = returnBillNum;
    }

    public double getSevenAvgBillNum() {
        return sevenAvgBillNum;
    }

    public void setSevenAvgBillNum(double sevenAvgBillNum) {
        this.sevenAvgBillNum = sevenAvgBillNum;
    }

    public double getSevenBillAmount() {
        return sevenBillAmount;
    }

    public void setSevenBillAmount(double sevenBillAmount) {
        this.sevenBillAmount = sevenBillAmount;
    }

    public int getSevenBillNum() {
        return sevenBillNum;
    }

    public void setSevenBillNum(int sevenBillNum) {
        this.sevenBillNum = sevenBillNum;
    }

    public double getSevenDayAvgAmount() {
        return sevenDayAvgAmount;
    }

    public void setSevenDayAvgAmount(double sevenDayAvgAmount) {
        this.sevenDayAvgAmount = sevenDayAvgAmount;
    }

    public double getThirtyBillAmount() {
        return thirtyBillAmount;
    }

    public void setThirtyBillAmount(double thirtyBillAmount) {
        this.thirtyBillAmount = thirtyBillAmount;
    }

    public int getThirtyBillNum() {
        return thirtyBillNum;
    }

    public void setThirtyBillNum(int thirtyBillNum) {
        this.thirtyBillNum = thirtyBillNum;
    }

    public double getThirtyDayAvgAmount() {
        return thirtyDayAvgAmount;
    }

    public void setThirtyDayAvgAmount(double thirtyDayAvgAmount) {
        this.thirtyDayAvgAmount = thirtyDayAvgAmount;
    }

    public double getThirtyDayAvgBillNum() {
        return thirtyDayAvgBillNum;
    }

    public void setThirtyDayAvgBillNum(double thirtyDayAvgBillNum) {
        this.thirtyDayAvgBillNum = thirtyDayAvgBillNum;
    }

    public double getTodayBillAmount() {
        return todayBillAmount;
    }

    public void setTodayBillAmount(double todayBillAmount) {
        this.todayBillAmount = todayBillAmount;
    }

    public int getTodayBillNum() {
        return todayBillNum;
    }

    public void setTodayBillNum(int todayBillNum) {
        this.todayBillNum = todayBillNum;
    }

    public double getUnsettledBillAmount() {
        return unsettledBillAmount;
    }

    public void setUnsettledBillAmount(double unsettledBillAmount) {
        this.unsettledBillAmount = unsettledBillAmount;
    }

    public int getUnsettledBillNum() {
        return unsettledBillNum;
    }

    public void setUnsettledBillNum(int unsettledBillNum) {
        this.unsettledBillNum = unsettledBillNum;
    }

    public double getWeekScale() {
        return weekScale;
    }

    public void setWeekScale(double weekScale) {
        this.weekScale = weekScale;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public String getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(String businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public String getCooperationTime() {
        return cooperationTime;
    }

    public void setCooperationTime(String cooperationTime) {
        this.cooperationTime = cooperationTime;
    }
}
