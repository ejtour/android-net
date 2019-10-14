package com.hll_sc_app.bean.complain;

import java.util.List;

public class ComplainAddReq {
    private int sourceClient;
    private String purchaserShopName;
    private String imgUrls;
    private String complaintExplain;
    private int source;
    private String purchaserID;
    private String type;
    private String purchaserContact;
    private int actionType;
    private String reason;
    private String purchaserShopID;
    private String billID;
    private String purchaserName;
    private int target;
    private String supplyName;
    private String supplyID;
    private List<ProductsBean> products;

    public int getSourceClient() {
        return sourceClient;
    }

    public void setSourceClient(int sourceClient) {
        this.sourceClient = sourceClient;
    }

    public String getPurchaserShopName() {
        return purchaserShopName;
    }

    public void setPurchaserShopName(String purchaserShopName) {
        this.purchaserShopName = purchaserShopName;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
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

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurchaserContact() {
        return purchaserContact;
    }

    public void setPurchaserContact(String purchaserContact) {
        this.purchaserContact = purchaserContact;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
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

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        private String imgUrl;
        private double productPrice;
        private String productName;
        private String productID;
        private String detailID;
        private double adjustmentNum;
        private String saleUnitName;
        private double standardNum;
        private String productSpec;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getDetailID() {
            return detailID;
        }

        public void setDetailID(String detailID) {
            this.detailID = detailID;
        }

        public double getAdjustmentNum() {
            return adjustmentNum;
        }

        public void setAdjustmentNum(double adjustmentNum) {
            this.adjustmentNum = adjustmentNum;
        }

        public String getSaleUnitName() {
            return saleUnitName;
        }

        public void setSaleUnitName(String saleUnitName) {
            this.saleUnitName = saleUnitName;
        }

        public double getStandardNum() {
            return standardNum;
        }

        public void setStandardNum(double standardNum) {
            this.standardNum = standardNum;
        }

        public String getProductSpec() {
            return productSpec;
        }

        public void setProductSpec(String productSpec) {
            this.productSpec = productSpec;
        }
    }
}
