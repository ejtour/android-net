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
        dest.writeString(this.employeeID);
        dest.writeString(this.employeeName);
        dest.writeString(this.employeePhone);
    }
}
