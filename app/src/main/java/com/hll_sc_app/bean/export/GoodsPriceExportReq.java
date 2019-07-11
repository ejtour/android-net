package com.hll_sc_app.bean.export;

/**
 * 协议价管理-商品导出
 *
 * @author zhuyingsong
 * @since 2019/7/11
 */

public class GoodsPriceExportReq {
    private String email;
    private String groupID;
    private String isBindEmail;
    private SearchParamsBean searchParams;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(String isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public SearchParamsBean getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(SearchParamsBean searchParams) {
        this.searchParams = searchParams;
    }

    public static class SearchParamsBean {
        /**
         * 分类ID，多个逗号隔开
         */
        private String categoryID;
        /**
         * 报价结束日期
         */
        private String endDate;
        /**
         * 供应商集团ID
         */
        private String groupID;
        /**
         * 生效结束日期
         */
        private String priceEndDate;
        /**
         * 生效开始日期
         */
        private String priceStartDate;
        /**
         * 采购商集团ID
         */
        private String purchaserID;
        /**
         * 店铺ID，多个逗号隔开
         */
        private String shopIDs;
        /**
         * 报价开始日期
         */
        private String startDate;

        public String getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(String categoryID) {
            this.categoryID = categoryID;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getPriceEndDate() {
            return priceEndDate;
        }

        public void setPriceEndDate(String priceEndDate) {
            this.priceEndDate = priceEndDate;
        }

        public String getPriceStartDate() {
            return priceStartDate;
        }

        public void setPriceStartDate(String priceStartDate) {
            this.priceStartDate = priceStartDate;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getShopIDs() {
            return shopIDs;
        }

        public void setShopIDs(String shopIDs) {
            this.shopIDs = shopIDs;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }
}
