package com.hll_sc_app.bean.complain;

import java.util.List;

/*投诉管理列表响应*/
public class ComplainListResp {


    private int totalSize;
    private List<ComplainListBean> list;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ComplainListBean> getList() {
        return list;
    }

    public void setList(List<ComplainListBean> list) {
        this.list = list;
    }

    public static class ComplainListBean {
        private String purchaserContact;
        private int reason;
        private String supplyName;
        private String actionTime;
        private String imgUrls;
        private String typeName;
        private String supplyContact;
        private int source;
        private String interventionSource;
        private String purchaserShopID;
        private int type;
        private String userID;
        private String interventionTime;
        private String products;
        private String supplyShopName;
        private String groupLogoUrl;
        private String purchaserID;
        private String supplyID;
        private String billID;
        private int action;
        private String id;
        private String purchaserShopName;
        private String reasonName;
        private String interventionBy;
        private String interventionContact;
        private int sourceBusiness;
        private String sourceClient;
        private String actionBy;
        private int supplyShopID;
        private String complaintExplain;
        private String refundBillNo;
        private int operationIntervention;
        private String purchaserName;
        private int target;
        private String createBy;
        private String createTime;
        private int status;

        public String getPurchaserContact() {
            return purchaserContact;
        }

        public void setPurchaserContact(String purchaserContact) {
            this.purchaserContact = purchaserContact;
        }

        public int getReason() {
            return reason;
        }

        public void setReason(int reason) {
            this.reason = reason;
        }

        public String getSupplyName() {
            return supplyName;
        }

        public void setSupplyName(String supplyName) {
            this.supplyName = supplyName;
        }

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public String getImgUrls() {
            return imgUrls;
        }

        public void setImgUrls(String imgUrls) {
            this.imgUrls = imgUrls;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getSupplyContact() {
            return supplyContact;
        }

        public void setSupplyContact(String supplyContact) {
            this.supplyContact = supplyContact;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getInterventionSource() {
            return interventionSource;
        }

        public void setInterventionSource(String interventionSource) {
            this.interventionSource = interventionSource;
        }

        public String getPurchaserShopID() {
            return purchaserShopID;
        }

        public void setPurchaserShopID(String purchaserShopID) {
            this.purchaserShopID = purchaserShopID;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getInterventionTime() {
            return interventionTime;
        }

        public void setInterventionTime(String interventionTime) {
            this.interventionTime = interventionTime;
        }

        public String getProducts() {
            return products;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public String getSupplyShopName() {
            return supplyShopName;
        }

        public void setSupplyShopName(String supplyShopName) {
            this.supplyShopName = supplyShopName;
        }

        public String getGroupLogoUrl() {
            return groupLogoUrl;
        }

        public void setGroupLogoUrl(String groupLogoUrl) {
            this.groupLogoUrl = groupLogoUrl;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getSupplyID() {
            return supplyID;
        }

        public void setSupplyID(String supplyID) {
            this.supplyID = supplyID;
        }

        public String getBillID() {
            return billID;
        }

        public void setBillID(String billID) {
            this.billID = billID;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPurchaserShopName() {
            return purchaserShopName;
        }

        public void setPurchaserShopName(String purchaserShopName) {
            this.purchaserShopName = purchaserShopName;
        }

        public String getReasonName() {
            return reasonName;
        }

        public void setReasonName(String reasonName) {
            this.reasonName = reasonName;
        }

        public String getInterventionBy() {
            return interventionBy;
        }

        public void setInterventionBy(String interventionBy) {
            this.interventionBy = interventionBy;
        }

        public String getInterventionContact() {
            return interventionContact;
        }

        public void setInterventionContact(String interventionContact) {
            this.interventionContact = interventionContact;
        }

        public int getSourceBusiness() {
            return sourceBusiness;
        }

        public void setSourceBusiness(int sourceBusiness) {
            this.sourceBusiness = sourceBusiness;
        }

        public String getSourceClient() {
            return sourceClient;
        }

        public void setSourceClient(String sourceClient) {
            this.sourceClient = sourceClient;
        }

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
        }

        public int getSupplyShopID() {
            return supplyShopID;
        }

        public void setSupplyShopID(int supplyShopID) {
            this.supplyShopID = supplyShopID;
        }

        public String getComplaintExplain() {
            return complaintExplain;
        }

        public void setComplaintExplain(String complaintExplain) {
            this.complaintExplain = complaintExplain;
        }

        public String getRefundBillNo() {
            return refundBillNo;
        }

        public void setRefundBillNo(String refundBillNo) {
            this.refundBillNo = refundBillNo;
        }

        public int getOperationIntervention() {
            return operationIntervention;
        }

        public void setOperationIntervention(int operationIntervention) {
            this.operationIntervention = operationIntervention;
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

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
