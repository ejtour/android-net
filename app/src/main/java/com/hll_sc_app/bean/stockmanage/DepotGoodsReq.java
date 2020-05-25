package com.hll_sc_app.bean.stockmanage;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/15
 */

public class DepotGoodsReq {
    private List<String> deleteProductIDList;
    private String groupID;
    private String houseID;
    private int isWholeCountry;
    private List<DepotGoodsBean> warehouseStoreProductList;

    public List<String> getDeleteProductIDList() {
        return deleteProductIDList;
    }

    public void setDeleteProductIDList(List<String> deleteProductIDList) {
        this.deleteProductIDList = deleteProductIDList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public int getIsWholeCountry() {
        return isWholeCountry;
    }

    public void setIsWholeCountry(int isWholeCountry) {
        this.isWholeCountry = isWholeCountry;
    }

    public List<DepotGoodsBean> getWarehouseStoreProductList() {
        return warehouseStoreProductList;
    }

    public void setWarehouseStoreProductList(List<DepotGoodsBean> warehouseStoreProductList) {
        this.warehouseStoreProductList = warehouseStoreProductList;
    }

    public static class DepotGoodsBean{
        private String productCode;
        private String productID;
        private String productName;

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}
