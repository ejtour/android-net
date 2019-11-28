package com.hll_sc_app.bean.cooperation;

import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

public class CooperationShopListResp {
    private List<PurchaserShopBean> shopList;
    private int shopTotal;
    private int newTotal;
    private CooperationPurchaserDetail groupInfo;

    public List<PurchaserShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<PurchaserShopBean> shopList) {
        this.shopList = shopList;
    }

    public int getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(int shopTotal) {
        this.shopTotal = shopTotal;
    }

    public int getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(int newTotal) {
        this.newTotal = newTotal;
    }

    public CooperationPurchaserDetail getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(CooperationPurchaserDetail groupInfo) {
        this.groupInfo = groupInfo;
    }

    /*public static class GroupInfoBean{
        private int readStatus;
        private String defaultAccountPeriod;
        private int shopNum;
        private String agreeTime;
        private String defaultDeliveryWay;
        private String entityIDNo;
        private String licencePhotoUrl;
        private int isWarehouse;
        private int action;
        private int defaultAccountPeriodType;
        private String defaultDeliveryPeriod;
        private String groupCity;
        private long applyTime;
        private String fax;
        private String reply;
        private String verification;
        private int isThird;
        private int isCertified;
        private int warehouseActive;
        private String groupID;
        private int isCooperation;
        private String licenseName;
        private String linkman;
        private String logoUrl;
        private String purchaserName;
        private String extGroupID;
        private String groupName;
        private String groupProvince;
        private String frontImg;
        private String groupAddress;
        private String customerID;
        private String name;
        private int warehouseStatus;
        private String defaultSettlementWay;
        private String cooperationID;
        private String purchaserNamePinyin;
        private String groupDistrict;
        private int status;
        private String actionTime;
        private int businessModel;
        private int returnAudit;
        private int thirdStatus;
        private String businessEntity;
        private int originator;
        private String defaultSettleDate;
        private String groupMail;
        private String businessNo;
        private String createby;
        private String purchaserID;
        private int newShopNum;
        private int groupActiveLabel;
        private String startTime;
        private int applyShopNum;
        private String actionBy;
        private String mobile;
        private int inspector;
        private int cooperationActive;
        private int customerLevel;
        private String actionType;
        private int maintainLevel;
        private int warehouseOriginator;
        private String createTime;
        private int groupActive;
        private String endTime;
        private int resourceType;

        public int getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(int readStatus) {
            this.readStatus = readStatus;
        }

        public String getDefaultAccountPeriod() {
            return defaultAccountPeriod;
        }

        public void setDefaultAccountPeriod(String defaultAccountPeriod) {
            this.defaultAccountPeriod = defaultAccountPeriod;
        }

        public int getShopNum() {
            return shopNum;
        }

        public void setShopNum(int shopNum) {
            this.shopNum = shopNum;
        }

        public String getAgreeTime() {
            return agreeTime;
        }

        public void setAgreeTime(String agreeTime) {
            this.agreeTime = agreeTime;
        }

        public String getDefaultDeliveryWay() {
            return defaultDeliveryWay;
        }

        public void setDefaultDeliveryWay(String defaultDeliveryWay) {
            this.defaultDeliveryWay = defaultDeliveryWay;
        }

        public String getEntityIDNo() {
            return entityIDNo;
        }

        public void setEntityIDNo(String entityIDNo) {
            this.entityIDNo = entityIDNo;
        }

        public String getLicencePhotoUrl() {
            return licencePhotoUrl;
        }

        public void setLicencePhotoUrl(String licencePhotoUrl) {
            this.licencePhotoUrl = licencePhotoUrl;
        }

        public int getIsWarehouse() {
            return isWarehouse;
        }

        public void setIsWarehouse(int isWarehouse) {
            this.isWarehouse = isWarehouse;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public int getDefaultAccountPeriodType() {
            return defaultAccountPeriodType;
        }

        public void setDefaultAccountPeriodType(int defaultAccountPeriodType) {
            this.defaultAccountPeriodType = defaultAccountPeriodType;
        }

        public String getDefaultDeliveryPeriod() {
            return defaultDeliveryPeriod;
        }

        public void setDefaultDeliveryPeriod(String defaultDeliveryPeriod) {
            this.defaultDeliveryPeriod = defaultDeliveryPeriod;
        }

        public String getGroupCity() {
            return groupCity;
        }

        public void setGroupCity(String groupCity) {
            this.groupCity = groupCity;
        }

        public long getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(long applyTime) {
            this.applyTime = applyTime;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getVerification() {
            return verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }

        public int getIsThird() {
            return isThird;
        }

        public void setIsThird(int isThird) {
            this.isThird = isThird;
        }

        public int getIsCertified() {
            return isCertified;
        }

        public void setIsCertified(int isCertified) {
            this.isCertified = isCertified;
        }

        public int getWarehouseActive() {
            return warehouseActive;
        }

        public void setWarehouseActive(int warehouseActive) {
            this.warehouseActive = warehouseActive;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public int getIsCooperation() {
            return isCooperation;
        }

        public void setIsCooperation(int isCooperation) {
            this.isCooperation = isCooperation;
        }

        public String getLicenseName() {
            return licenseName;
        }

        public void setLicenseName(String licenseName) {
            this.licenseName = licenseName;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getExtGroupID() {
            return extGroupID;
        }

        public void setExtGroupID(String extGroupID) {
            this.extGroupID = extGroupID;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupProvince() {
            return groupProvince;
        }

        public void setGroupProvince(String groupProvince) {
            this.groupProvince = groupProvince;
        }

        public String getFrontImg() {
            return frontImg;
        }

        public void setFrontImg(String frontImg) {
            this.frontImg = frontImg;
        }

        public String getGroupAddress() {
            return groupAddress;
        }

        public void setGroupAddress(String groupAddress) {
            this.groupAddress = groupAddress;
        }

        public String getCustomerID() {
            return customerID;
        }

        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWarehouseStatus() {
            return warehouseStatus;
        }

        public void setWarehouseStatus(int warehouseStatus) {
            this.warehouseStatus = warehouseStatus;
        }

        public String getDefaultSettlementWay() {
            return defaultSettlementWay;
        }

        public void setDefaultSettlementWay(String defaultSettlementWay) {
            this.defaultSettlementWay = defaultSettlementWay;
        }

        public String getCooperationID() {
            return cooperationID;
        }

        public void setCooperationID(String cooperationID) {
            this.cooperationID = cooperationID;
        }

        public String getPurchaserNamePinyin() {
            return purchaserNamePinyin;
        }

        public void setPurchaserNamePinyin(String purchaserNamePinyin) {
            this.purchaserNamePinyin = purchaserNamePinyin;
        }

        public String getGroupDistrict() {
            return groupDistrict;
        }

        public void setGroupDistrict(String groupDistrict) {
            this.groupDistrict = groupDistrict;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public int getBusinessModel() {
            return businessModel;
        }

        public void setBusinessModel(int businessModel) {
            this.businessModel = businessModel;
        }

        public int getReturnAudit() {
            return returnAudit;
        }

        public void setReturnAudit(int returnAudit) {
            this.returnAudit = returnAudit;
        }

        public int getThirdStatus() {
            return thirdStatus;
        }

        public void setThirdStatus(int thirdStatus) {
            this.thirdStatus = thirdStatus;
        }

        public String getBusinessEntity() {
            return businessEntity;
        }

        public void setBusinessEntity(String businessEntity) {
            this.businessEntity = businessEntity;
        }

        public int getOriginator() {
            return originator;
        }

        public void setOriginator(int originator) {
            this.originator = originator;
        }

        public String getDefaultSettleDate() {
            return defaultSettleDate;
        }

        public void setDefaultSettleDate(String defaultSettleDate) {
            this.defaultSettleDate = defaultSettleDate;
        }

        public String getGroupMail() {
            return groupMail;
        }

        public void setGroupMail(String groupMail) {
            this.groupMail = groupMail;
        }

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public int getNewShopNum() {
            return newShopNum;
        }

        public void setNewShopNum(int newShopNum) {
            this.newShopNum = newShopNum;
        }

        public int getGroupActiveLabel() {
            return groupActiveLabel;
        }

        public void setGroupActiveLabel(int groupActiveLabel) {
            this.groupActiveLabel = groupActiveLabel;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getApplyShopNum() {
            return applyShopNum;
        }

        public void setApplyShopNum(int applyShopNum) {
            this.applyShopNum = applyShopNum;
        }

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getInspector() {
            return inspector;
        }

        public void setInspector(int inspector) {
            this.inspector = inspector;
        }

        public int getCooperationActive() {
            return cooperationActive;
        }

        public void setCooperationActive(int cooperationActive) {
            this.cooperationActive = cooperationActive;
        }

        public int getCustomerLevel() {
            return customerLevel;
        }

        public void setCustomerLevel(int customerLevel) {
            this.customerLevel = customerLevel;
        }

        public String getActionType() {
            return actionType;
        }

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        public int getMaintainLevel() {
            return maintainLevel;
        }

        public void setMaintainLevel(int maintainLevel) {
            this.maintainLevel = maintainLevel;
        }

        public int getWarehouseOriginator() {
            return warehouseOriginator;
        }

        public void setWarehouseOriginator(int warehouseOriginator) {
            this.warehouseOriginator = warehouseOriginator;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getGroupActive() {
            return groupActive;
        }

        public void setGroupActive(int groupActive) {
            this.groupActive = groupActive;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }
    }*/
}
