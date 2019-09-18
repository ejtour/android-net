package com.hll_sc_app.bean.stockmanage;

import java.util.List;

/*合作关系店铺列表*/
public class CustomerSendManageListResp {

    private List<RecordsBean> records;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {

        private String defaultAccountPeriod;
        private String actionTime;
        private String salesmanID;
        private String agreeTime;
        private String salesmanPhone;
        private String shopName;
        private String accountPeriod;
        private String purchaserID;
        private String driverID;
        private String warehouseID;
        private int action;
        private int defaultAccountPeriodType;
        private String id;
        private String salesmanName;
        private String houseID;
        private String groupID;
        private String purchaserName;
        private String imgUrl;
        private String settlementWay;
        private int accountPeriodType;
        private String createTime;
        private String customerHighSeas;
        private String defaultSettlementWay;
        private String manageTime;
        private String driverName;
        private String shopID;
        private String houseName;
        private int status;

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getDefaultAccountPeriod() {
            return defaultAccountPeriod;
        }

        public void setDefaultAccountPeriod(String defaultAccountPeriod) {
            this.defaultAccountPeriod = defaultAccountPeriod;
        }

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public String getSalesmanID() {
            return salesmanID;
        }

        public void setSalesmanID(String salesmanID) {
            this.salesmanID = salesmanID;
        }

        public String getAgreeTime() {
            return agreeTime;
        }

        public void setAgreeTime(String agreeTime) {
            this.agreeTime = agreeTime;
        }

        public String getSalesmanPhone() {
            return salesmanPhone;
        }

        public void setSalesmanPhone(String salesmanPhone) {
            this.salesmanPhone = salesmanPhone;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getAccountPeriod() {
            return accountPeriod;
        }

        public void setAccountPeriod(String accountPeriod) {
            this.accountPeriod = accountPeriod;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getDriverID() {
            return driverID;
        }

        public void setDriverID(String driverID) {
            this.driverID = driverID;
        }

        public String getWarehouseID() {
            return warehouseID;
        }

        public void setWarehouseID(String warehouseID) {
            this.warehouseID = warehouseID;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSalesmanName() {
            return salesmanName;
        }

        public void setSalesmanName(String salesmanName) {
            this.salesmanName = salesmanName;
        }

        public String getHouseID() {
            return houseID;
        }

        public void setHouseID(String houseID) {
            this.houseID = houseID;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getSettlementWay() {
            return settlementWay;
        }

        public void setSettlementWay(String settlementWay) {
            this.settlementWay = settlementWay;
        }

        public int getAccountPeriodType() {
            return accountPeriodType;
        }

        public void setAccountPeriodType(int accountPeriodType) {
            this.accountPeriodType = accountPeriodType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCustomerHighSeas() {
            return customerHighSeas;
        }

        public void setCustomerHighSeas(String customerHighSeas) {
            this.customerHighSeas = customerHighSeas;
        }

        public String getDefaultSettlementWay() {
            return defaultSettlementWay;
        }

        public void setDefaultSettlementWay(String defaultSettlementWay) {
            this.defaultSettlementWay = defaultSettlementWay;
        }

        public String getManageTime() {
            return manageTime;
        }

        public void setManageTime(String manageTime) {
            this.manageTime = manageTime;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
