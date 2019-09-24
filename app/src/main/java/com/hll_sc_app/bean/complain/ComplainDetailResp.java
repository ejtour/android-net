package com.hll_sc_app.bean.complain;

import android.os.Parcel;
import android.os.Parcelable;

/*投诉详情*/
public class ComplainDetailResp implements Parcelable {

    private String complaintExplain;
    private String driverName;
    private String id;
    private String imgUrls;
    private String lineName;
    private String products;
    private String purchaserContact;
    private String reason;
    private String reasonName;
    private String supplyContact;
    private String type;
    private String typeName;
    private String purchaserShopName;
    private String purchaserName;
    private String billID;
    private String createBy;
    private String interventionContact;
    private String subBillID;
    private String purchaserID;
    private String purchaserShopID;
    public ComplainDetailResp() {
    }
    private int target;
    private int operationIntervention;


    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public int getOperationIntervention() {
        return operationIntervention;
    }

    public void setOperationIntervention(int operationIntervention) {
        this.operationIntervention = operationIntervention;
    }

    public String getInterventionContact() {
        return interventionContact;
    }

    public void setInterventionContact(String interventionContact) {
        this.interventionContact = interventionContact;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getPurchaserShopName() {
        return purchaserShopName;
    }

    public void setPurchaserShopName(String purchaserShopName) {
        this.purchaserShopName = purchaserShopName;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getComplaintExplain() {
        return complaintExplain;
    }

    public void setComplaintExplain(String complaintExplain) {
        this.complaintExplain = complaintExplain;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getPurchaserContact() {
        return purchaserContact;
    }

    public void setPurchaserContact(String purchaserContact) {
        this.purchaserContact = purchaserContact;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getSupplyContact() {
        return supplyContact;
    }

    public void setSupplyContact(String supplyContact) {
        this.supplyContact = supplyContact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.complaintExplain);
        dest.writeString(this.driverName);
        dest.writeString(this.id);
        dest.writeString(this.imgUrls);
        dest.writeString(this.lineName);
        dest.writeString(this.products);
        dest.writeString(this.purchaserContact);
        dest.writeString(this.reason);
        dest.writeString(this.reasonName);
        dest.writeString(this.supplyContact);
        dest.writeString(this.type);
        dest.writeString(this.typeName);
        dest.writeString(this.purchaserShopName);
        dest.writeString(this.purchaserName);
        dest.writeString(this.billID);
        dest.writeString(this.createBy);
        dest.writeString(this.interventionContact);
        dest.writeString(this.subBillID);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserShopID);
        dest.writeInt(this.target);
        dest.writeInt(this.operationIntervention);
    }

    protected ComplainDetailResp(Parcel in) {
        this.complaintExplain = in.readString();
        this.driverName = in.readString();
        this.id = in.readString();
        this.imgUrls = in.readString();
        this.lineName = in.readString();
        this.products = in.readString();
        this.purchaserContact = in.readString();
        this.reason = in.readString();
        this.reasonName = in.readString();
        this.supplyContact = in.readString();
        this.type = in.readString();
        this.typeName = in.readString();
        this.purchaserShopName = in.readString();
        this.purchaserName = in.readString();
        this.billID = in.readString();
        this.createBy = in.readString();
        this.interventionContact = in.readString();
        this.subBillID = in.readString();
        this.purchaserID = in.readString();
        this.purchaserShopID = in.readString();
        this.target = in.readInt();
        this.operationIntervention = in.readInt();
    }

    public static final Creator<ComplainDetailResp> CREATOR = new Creator<ComplainDetailResp>() {
        @Override
        public ComplainDetailResp createFromParcel(Parcel source) {
            return new ComplainDetailResp(source);
        }

        @Override
        public ComplainDetailResp[] newArray(int size) {
            return new ComplainDetailResp[size];
        }
    };
}
