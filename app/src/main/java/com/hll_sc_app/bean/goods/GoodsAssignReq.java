package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignReq {
    private List<GoodsAssignProductReq> details;
    private int flag;
    private String groupID;
    private String mainID;
    private String purchaserID;
    private String purchaserName;
    private List<String> purchaserShopIDs;
    private int type;

    public List<GoodsAssignProductReq> getDetails() {
        return details;
    }

    public void setDetails(List<GoodsAssignProductReq> details) {
        this.details = details;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
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

    public List<String> getPurchaserShopIDs() {
        return purchaserShopIDs;
    }

    public void setPurchaserShopIDs(List<String> purchaserShopIDs) {
        this.purchaserShopIDs = purchaserShopIDs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class GoodsAssignProductReq{
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
