package com.hll_sc_app.bean.stockmanage;

import java.util.List;

/*仓库日志查询请响应*/
public class StockLogResp {

    private int totalSize;
    private List<StockLog> list;

    public List<StockLog> getList() {
        return list;
    }

    public void setList(List<StockLog> list) {
        this.list = list;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public static class StockLog {
        private String specID;
        private String actionTime;
        private String houseID;
        private String productID;
        private String cargoOwnerID;
        private String groupID;
        private String sourceNo;
        private int source;
        private String productName;
        private String houseName;
        private String businessNo;
        private String productCode;
        private String totalStockChange;
        private String businessTypeName;
        private String createTime;
        private int inOrOut;
        private String houseCode;
        private int action;
        private String id;
        private String usableStockChange;
        private int businessType;
        private String stockChange;
        private String occupiedStockChange;
        private int verifyType;

        public String getSpecID() {
            return specID;
        }

        public void setSpecID(String specID) {
            this.specID = specID;
        }

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public String getHouseID() {
            return houseID;
        }

        public void setHouseID(String houseID) {
            this.houseID = houseID;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getCargoOwnerID() {
            return cargoOwnerID;
        }

        public void setCargoOwnerID(String cargoOwnerID) {
            this.cargoOwnerID = cargoOwnerID;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getSourceNo() {
            return sourceNo;
        }

        public void setSourceNo(String sourceNo) {
            this.sourceNo = sourceNo;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getTotalStockChange() {
            return totalStockChange;
        }

        public void setTotalStockChange(String totalStockChange) {
            this.totalStockChange = totalStockChange;
        }

        public String getBusinessTypeName() {
            return businessTypeName;
        }

        public void setBusinessTypeName(String businessTypeName) {
            this.businessTypeName = businessTypeName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getInOrOut() {
            return inOrOut;
        }

        public void setInOrOut(int inOrOut) {
            this.inOrOut = inOrOut;
        }

        public String getHouseCode() {
            return houseCode;
        }

        public void setHouseCode(String houseCode) {
            this.houseCode = houseCode;
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

        public String getUsableStockChange() {
            return usableStockChange;
        }

        public void setUsableStockChange(String usableStockChange) {
            this.usableStockChange = usableStockChange;
        }

        public int getBusinessType() {
            return businessType;
        }

        public void setBusinessType(int businessType) {
            this.businessType = businessType;
        }

        public String getStockChange() {
            return stockChange;
        }

        public void setStockChange(String stockChange) {
            this.stockChange = stockChange;
        }

        public String getOccupiedStockChange() {
            return occupiedStockChange;
        }

        public void setOccupiedStockChange(String occupiedStockChange) {
            this.occupiedStockChange = occupiedStockChange;
        }

        public int getVerifyType() {
            return verifyType;
        }

        public void setVerifyType(int verifyType) {
            this.verifyType = verifyType;
        }
    }
}
