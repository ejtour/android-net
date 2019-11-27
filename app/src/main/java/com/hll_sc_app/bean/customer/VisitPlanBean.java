package com.hll_sc_app.bean.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.goods.PurchaserBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/23
 */

public class VisitPlanBean implements Parcelable {
    public static final Creator<VisitPlanBean> CREATOR = new Creator<VisitPlanBean>() {
        @Override
        public VisitPlanBean createFromParcel(Parcel in) {
            return new VisitPlanBean(in);
        }

        @Override
        public VisitPlanBean[] newArray(int size) {
            return new VisitPlanBean[size];
        }
    };
    @Expose(deserialize = false)
    int actionType;
    private String attentions;
    String customerAddress;
    String customerCity;
    String customerDistrict;
    String customerID;
    String customerName;
    String customerProvince;
    int customerType;
    String employeeID;
    String groupID;
    String id;
    int maintainLevel;
    String purchaserID;
    int visitGoal;
    String visitPersonnel;
    String visitTime;
    int visitWay;

    public void inflateData(Parcelable parcelable){
        if (parcelable instanceof CustomerBean) {
            CustomerBean customerBean = (CustomerBean) parcelable;
            setCustomerID(customerBean.getId());
            setPurchaserID(null);
            setCustomerName(customerBean.getCustomerName());
            setCustomerAddress(customerBean.getCustomerAddress());
            setCustomerCity(customerBean.getCustomerCity());
            setCustomerDistrict(customerBean.getCustomerDistrict());
            setCustomerProvince(customerBean.getCustomerProvince());
        } else if (parcelable instanceof PurchaserShopBean) {
            PurchaserShopBean shopBean = (PurchaserShopBean) parcelable;
            setCustomerID(shopBean.getShopID());
            setPurchaserID(shopBean.getPurchaserID());
            setCustomerName(shopBean.getShopName());
            setCustomerAddress(shopBean.getShopAddress());
            setCustomerCity(shopBean.getShopCity());
            setCustomerDistrict(shopBean.getShopDistrict());
            setCustomerProvince(shopBean.getShopProvince());
        } else if (parcelable instanceof PurchaserBean) {
            PurchaserBean purchaserBean = (PurchaserBean) parcelable;
            setCustomerID("0");
            setPurchaserID(purchaserBean.getPurchaserID());
            setCustomerName(purchaserBean.getPurchaserName());
            setCustomerAddress(purchaserBean.getGroupAddress());
            setCustomerCity(purchaserBean.getGroupCity());
            setCustomerDistrict(purchaserBean.getGroupDistrict());
            setCustomerProvince(purchaserBean.getGroupProvince());
        }
    }

    public VisitPlanBean() {
    }

    protected VisitPlanBean(Parcel in) {
        actionType = in.readInt();
        attentions = in.readString();
        customerAddress = in.readString();
        customerCity = in.readString();
        customerDistrict = in.readString();
        customerID = in.readString();
        customerName = in.readString();
        customerProvince = in.readString();
        customerType = in.readInt();
        employeeID = in.readString();
        groupID = in.readString();
        id = in.readString();
        maintainLevel = in.readInt();
        purchaserID = in.readString();
        visitGoal = in.readInt();
        visitPersonnel = in.readString();
        visitTime = in.readString();
        visitWay = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actionType);
        dest.writeString(attentions);
        dest.writeString(customerAddress);
        dest.writeString(customerCity);
        dest.writeString(customerDistrict);
        dest.writeString(customerID);
        dest.writeString(customerName);
        dest.writeString(customerProvince);
        dest.writeInt(customerType);
        dest.writeString(employeeID);
        dest.writeString(groupID);
        dest.writeString(id);
        dest.writeInt(maintainLevel);
        dest.writeString(purchaserID);
        dest.writeInt(visitGoal);
        dest.writeString(visitPersonnel);
        dest.writeString(visitTime);
        dest.writeInt(visitWay);
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

    public String getAttentions() {
        return attentions;
    }

    public void setAttentions(String attentions) {
        this.attentions = attentions;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        this.customerDistrict = customerDistrict;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaintainLevel() {
        return maintainLevel;
    }

    public void setMaintainLevel(int maintainLevel) {
        this.maintainLevel = maintainLevel;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public int getVisitGoal() {
        return visitGoal;
    }

    public void setVisitGoal(int visitGoal) {
        this.visitGoal = visitGoal;
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

    public int getVisitWay() {
        return visitWay;
    }

    public void setVisitWay(int visitWay) {
        this.visitWay = visitWay;
    }
}
