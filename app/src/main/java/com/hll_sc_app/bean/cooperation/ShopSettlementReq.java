package com.hll_sc_app.bean.cooperation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 修改合作关系支付相关设置
 *
 * @author zhuyingsong
 * @date 2019-07-18
 */
public class ShopSettlementReq implements Parcelable {
    public static final Creator<ShopSettlementReq> CREATOR = new Creator<ShopSettlementReq>() {
        @Override
        public ShopSettlementReq createFromParcel(Parcel source) {
            return new ShopSettlementReq(source);
        }

        @Override
        public ShopSettlementReq[] newArray(int size) {
            return new ShopSettlementReq[size];
        }
    };
    private String actionType;
    private String changeAllShops;
    private String deliveryWay;
    private String groupID;
    private String purchaserID;
    private List<String> shopIds;
    /**
     * 结算方式
     */
    private String settlementWay;
    /**
     * 账期支付日
     */
    private String settleDate;
    /**
     * 账期日类型,0-未设置,1-按周,2-按月
     */
    private String accountPeriodType;
    /**
     * 账期日
     */
    private String accountPeriod;
    private String employeeID;
    private String employeeName;
    private String employeePhone;

    public ShopSettlementReq() {
    }

    protected ShopSettlementReq(Parcel in) {
        this.actionType = in.readString();
        this.changeAllShops = in.readString();
        this.deliveryWay = in.readString();
        this.groupID = in.readString();
        this.purchaserID = in.readString();
        this.shopIds = in.createStringArrayList();
        this.settlementWay = in.readString();
        this.settleDate = in.readString();
        this.accountPeriodType = in.readString();
        this.accountPeriod = in.readString();
        this.employeeID = in.readString();
        this.employeeName = in.readString();
        this.employeePhone = in.readString();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getChangeAllShops() {
        return changeAllShops;
    }

    public void setChangeAllShops(String changeAllShops) {
        this.changeAllShops = changeAllShops;
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public List<String> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<String> shopIds) {
        this.shopIds = shopIds;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionType);
        dest.writeString(this.changeAllShops);
        dest.writeString(this.deliveryWay);
        dest.writeString(this.groupID);
        dest.writeString(this.purchaserID);
        dest.writeStringList(this.shopIds);
        dest.writeString(this.settlementWay);
        dest.writeString(this.settleDate);
        dest.writeString(this.accountPeriodType);
        dest.writeString(this.accountPeriod);
        dest.writeString(this.employeeID);
        dest.writeString(this.employeeName);
        dest.writeString(this.employeePhone);
    }
}
