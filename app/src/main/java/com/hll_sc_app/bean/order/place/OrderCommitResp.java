package com.hll_sc_app.bean.order.place;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/20
 */

public class OrderCommitResp {
    private String masterBillIDs;
    private List<StockErrorBean> stockErrorList;

    public String getMasterBillIDs() {
        return masterBillIDs;
    }

    public void setMasterBillIDs(String masterBillIDs) {
        this.masterBillIDs = masterBillIDs;
    }

    public List<StockErrorBean> getStockErrorList() {
        return stockErrorList;
    }

    public void setStockErrorList(List<StockErrorBean> stockErrorList) {
        this.stockErrorList = stockErrorList;
    }

    public static class StockErrorBean {
        private String productName;
        private String productSpecID;
        private String saleUnitName;
        private String specContent;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductSpecID() {
            return productSpecID;
        }

        public void setProductSpecID(String productSpecID) {
            this.productSpecID = productSpecID;
        }

        public String getSaleUnitName() {
            return saleUnitName;
        }

        public void setSaleUnitName(String saleUnitName) {
            this.saleUnitName = saleUnitName;
        }

        public String getSpecContent() {
            return specContent;
        }

        public void setSpecContent(String specContent) {
            this.specContent = specContent;
        }
    }
}
