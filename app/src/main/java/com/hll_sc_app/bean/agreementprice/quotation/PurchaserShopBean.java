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
    private String salesmanID;
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
    private String manageTime;
    private int isWarehouse;
    private String extShopID;
    private List<CooperationSourceBean> cooperationSource;

    public PurchaserShopBean deepCopy() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }

    public String getExtShopID() {
        return extShopID;
    }

    public void setExtShopID(String extShopID) {
        this.extShopID = extShopID;
    }

    public int getIsWarehouse() {
        return isWarehouse;
    }

    public void setIsWarehouse(int isWarehouse) {
        this.isWarehouse = isWarehouse;
    }

    public String getManageTime() {
        return manageTime;
    }

    public void setManageTime(String manageTime) {
        this.manageTime = manageTime;
    }

    public PurchaserShopBean() {
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

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
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
        dest.writeString(this.salesRepresentativeName);
        dest.writeString(this.settleDate);
        dest.writeString(this.driverName);
        dest.writeString(this.salesRepresentativeID);
        dest.writeString(this.deliveryWay);
        dest.writeString(this.agreeTime);
        dest.writeString(this.deliveryPeriod);
        dest.writeString(this.driverID);
        dest.writeString(this.salesmanName);
        dest.writeString(this.salesmanPhone);
        dest.writeString(this.salesmanID);
        dest.writeInt(this.cooperationActive);
        dest.writeInt(this.fallFlag);
        dest.writeInt(this.newFlag);
        dest.writeDouble(this.actualSevenAvgBillNum);
        dest.writeDouble(this.currentAvgAmount);
        dest.writeInt(this.currentMonthBillNum);
        dest.writeInt(this.currentWeekBillNum);
        dest.writeString(this.lastBillTime);
        dest.writeString(this.lastVisitTime);
        dest.writeDouble(this.monthScale);
        dest.writeInt(this.preMonthBillNum);
        dest.writeInt(this.preWeekBillNum);
        dest.writeInt(this.returnBillNum);
        dest.writeDouble(this.sevenAvgBillNum);
        dest.writeDouble(this.sevenBillAmount);
        dest.writeInt(this.sevenBillNum);
        dest.writeDouble(this.sevenDayAvgAmount);
        dest.writeDouble(this.thirtyBillAmount);
        dest.writeInt(this.thirtyBillNum);
        dest.writeDouble(this.thirtyDayAvgAmount);
        dest.writeDouble(this.thirtyDayAvgBillNum);
        dest.writeDouble(this.todayBillAmount);
        dest.writeInt(this.todayBillNum);
        dest.writeDouble(this.unsettledBillAmount);
        dest.writeInt(this.unsettledBillNum);
        dest.writeDouble(this.weekScale);
        dest.writeString(this.businessStartTime);
        dest.writeString(this.businessEndTime);
        dest.writeString(this.cooperationTime);
        dest.writeString(this.manageTime);
        dest.writeInt(this.isWarehouse);
        dest.writeString(this.extShopID);
        dest.writeTypedList(this.cooperationSource);
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
        this.salesRepresentativeName = in.readString();
        this.settleDate = in.readString();
        this.driverName = in.readString();
        this.salesRepresentativeID = in.readString();
        this.deliveryWay = in.readString();
        this.agreeTime = in.readString();
        this.deliveryPeriod = in.readString();
        this.driverID = in.readString();
        this.salesmanName = in.readString();
        this.salesmanPhone = in.readString();
        this.salesmanID = in.readString();
        this.cooperationActive = in.readInt();
        this.fallFlag = in.readInt();
        this.newFlag = in.readInt();
        this.actualSevenAvgBillNum = in.readDouble();
        this.currentAvgAmount = in.readDouble();
        this.currentMonthBillNum = in.readInt();
        this.currentWeekBillNum = in.readInt();
        this.lastBillTime = in.readString();
        this.lastVisitTime = in.readString();
        this.monthScale = in.readDouble();
        this.preMonthBillNum = in.readInt();
        this.preWeekBillNum = in.readInt();
        this.returnBillNum = in.readInt();
        this.sevenAvgBillNum = in.readDouble();
        this.sevenBillAmount = in.readDouble();
        this.sevenBillNum = in.readInt();
        this.sevenDayAvgAmount = in.readDouble();
        this.thirtyBillAmount = in.readDouble();
        this.thirtyBillNum = in.readInt();
        this.thirtyDayAvgAmount = in.readDouble();
        this.thirtyDayAvgBillNum = in.readDouble();
        this.todayBillAmount = in.readDouble();
        this.todayBillNum = in.readInt();
        this.unsettledBillAmount = in.readDouble();
        this.unsettledBillNum = in.readInt();
        this.weekScale = in.readDouble();
        this.businessStartTime = in.readString();
        this.businessEndTime = in.readString();
        this.cooperationTime = in.readString();
        this.manageTime = in.readString();
        this.isWarehouse = in.readInt();
        this.extShopID = in.readString();
        this.cooperationSource = in.createTypedArrayList(CooperationSourceBean.CREATOR);
    }

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
}
