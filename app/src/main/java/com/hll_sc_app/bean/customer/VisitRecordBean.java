package com.hll_sc_app.bean.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public class VisitRecordBean implements Parcelable {

    public static final Creator<VisitRecordBean> CREATOR = new Creator<VisitRecordBean>() {
        @Override
        public VisitRecordBean createFromParcel(Parcel in) {
            return new VisitRecordBean(in);
        }

        @Override
        public VisitRecordBean[] newArray(int size) {
            return new VisitRecordBean[size];
        }
    };
    @Expose(deserialize = false)
    private int actionType;
    private String customerAddress;
    private String visitResult;
    private String customerProvince;
    private String groupID;
    private String employeeID;
    private String customerCity;
    private int isOnSchedule;
    private int isActive;
    private String customerName;
    private String customerDistrict;
    private int customerType;
    private int maintainLevel;
    private String visitPersonnel;
    private String visitTime;
    private String purchaserID;
    private int visitWay;
    private String customerID;
    private String planID;
    private int visitGoal;
    private String id;
    private String nextVisitTime;

    public VisitRecordBean() {
    }

    protected VisitRecordBean(Parcel in) {
        actionType = in.readInt();
        customerAddress = in.readString();
        visitResult = in.readString();
        customerProvince = in.readString();
        groupID = in.readString();
        employeeID = in.readString();
        customerCity = in.readString();
        isOnSchedule = in.readInt();
        isActive = in.readInt();
        customerName = in.readString();
        customerDistrict = in.readString();
        customerType = in.readInt();
        maintainLevel = in.readInt();
        visitPersonnel = in.readString();
        visitTime = in.readString();
        purchaserID = in.readString();
        visitWay = in.readInt();
        customerID = in.readString();
        planID = in.readString();
        visitGoal = in.readInt();
        id = in.readString();
        nextVisitTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actionType);
        dest.writeString(customerAddress);
        dest.writeString(visitResult);
        dest.writeString(customerProvince);
        dest.writeString(groupID);
        dest.writeString(employeeID);
        dest.writeString(customerCity);
        dest.writeInt(isOnSchedule);
        dest.writeInt(isActive);
        dest.writeString(customerName);
        dest.writeString(customerDistrict);
        dest.writeInt(customerType);
        dest.writeInt(maintainLevel);
        dest.writeString(visitPersonnel);
        dest.writeString(visitTime);
        dest.writeString(purchaserID);
        dest.writeInt(visitWay);
        dest.writeString(customerID);
        dest.writeString(planID);
        dest.writeInt(visitGoal);
        dest.writeString(id);
        dest.writeString(nextVisitTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public int getIsOnSchedule() {
        return isOnSchedule;
    }

    public void setIsOnSchedule(int isOnSchedule) {
        this.isOnSchedule = isOnSchedule;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        this.customerDistrict = customerDistrict;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public int getMaintainLevel() {
        return maintainLevel;
    }

    public void setMaintainLevel(int maintainLevel) {
        this.maintainLevel = maintainLevel;
    }

    public String getVisitPersonnel() {
        return visitPersonnel;
    }

    public void setVisitPersonnel(String visitPersonnel) {
        this.visitPersonnel = visitPersonnel;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public int getVisitWay() {
        return visitWay;
    }

    public void setVisitWay(int visitWay) {
        this.visitWay = visitWay;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public int getVisitGoal() {
        return visitGoal;
    }

    public void setVisitGoal(int visitGoal) {
        this.visitGoal = visitGoal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNextVisitTime() {
        return nextVisitTime;
    }

    public void setNextVisitTime(String nextVisitTime) {
        this.nextVisitTime = nextVisitTime;
    }
}
