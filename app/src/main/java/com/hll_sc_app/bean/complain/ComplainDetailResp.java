package com.hll_sc_app.bean.complain;

/*投诉详情*/
public class ComplainDetailResp {

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
    private int target;
    private int operationIntervention;

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
}
