package com.hll_sc_app.bean.complain;

import java.util.List;

/**
 * 投诉协商历史响应
 */
public class ComplainHistoryResp {

    private List<HistoryBean> list;

    public List<HistoryBean> getList() {
        return list;
    }

    public void setList(List<HistoryBean> list) {
        this.list = list;
    }

    public static class HistoryBean {
        private String reasonName;
        private String reason;
        private String supplyName;
        private String actionTime;
        private String actionBy;
        private String imgUrls;
        private String typeName;
        private String complaintExplain;
        private int source;
        private int type;
        private String purchaserName;
        private String products;
        private String createBy;
        private String groupLogoUrl;
        private String purchaserID;
        private String complaintID;
        private String createTime;
        private String supplyID;
        private String billID;
        private int action;
        private String id;
        private String reply;
        private int status;

        public String getReasonName() {
            return reasonName;
        }

        public void setReasonName(String reasonName) {
            this.reasonName = reasonName;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
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

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
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

        public String getComplaintExplain() {
            return complaintExplain;
        }

        public void setComplaintExplain(String complaintExplain) {
            this.complaintExplain = complaintExplain;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getProducts() {
            return products;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
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

        public String getComplaintID() {
            return complaintID;
        }

        public void setComplaintID(String complaintID) {
            this.complaintID = complaintID;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
