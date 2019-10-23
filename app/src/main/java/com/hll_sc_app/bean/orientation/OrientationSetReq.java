package com.hll_sc_app.bean.orientation;

import java.util.List;

public class OrientationSetReq {

    private Integer flag;
    private String groupID;
    private String mainID;
    private String purchaserID;
    private String purchaserName;
    private Integer type;
    private List<String> productIDs;
    private List<String> purchaserShopIDs;
    private List<Details> details;

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getMainID() {
        return mainID;
    }

    public void setMainID(String mainID) {
        this.mainID = mainID;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(List<String> productIDs) {
        this.productIDs = productIDs;
    }

    public List<String> getPurchaserShopIDs() {
        return purchaserShopIDs;
    }

    public void setPurchaserShopIDs(List<String> purchaserShopIDs) {
        this.purchaserShopIDs = purchaserShopIDs;
    }


    public static class Details{
        private String productID;
        private List<String> specIDList;

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public List<String> getSpecIDList() {
            return specIDList;
        }

        public void setSpecIDList(List<String> specIDList) {
            this.specIDList = specIDList;
        }
    }
}

