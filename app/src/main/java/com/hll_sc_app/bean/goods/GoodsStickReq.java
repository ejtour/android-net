package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品置顶-100106
 *
 * @author zhuyingsong
 * @date 2019-07-02
 */
public class GoodsStickReq {
    private List<String> deleteProductIDs;
    private List<RecordsBean> records;

    public List<String> getDeleteProductIDs() {
        return deleteProductIDs;
    }

    public void setDeleteProductIDs(List<String> deleteProductIDs) {
        this.deleteProductIDs = deleteProductIDs;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * 集团ID
         */
        private String groupID;
        /**
         * 自定义分类一级ID
         */
        private String shopProductCategorySubID;
        private List<ListBean> list;

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getShopProductCategorySubID() {
            return shopProductCategorySubID;
        }

        public void setShopProductCategorySubID(String shopProductCategorySubID) {
            this.shopProductCategorySubID = shopProductCategorySubID;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * 商品ID
             */
            private String productID;
            /**
             * 自定义分类二级ID
             */
            private String shopProductCategoryThreeID;
            /**
             * sort
             */
            private String sort;

            public String getProductID() {
                return productID;
            }

            public void setProductID(String productID) {
                this.productID = productID;
            }

            public String getShopProductCategoryThreeID() {
                return shopProductCategoryThreeID;
            }

            public void setShopProductCategoryThreeID(String shopProductCategoryThreeID) {
                this.shopProductCategoryThreeID = shopProductCategoryThreeID;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }
    }
}
