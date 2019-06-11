package com.hll_sc_app.bean.goods;

import android.widget.GridLayout;

import java.util.List;

/**
 * 商品规格状态修改
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class SpecsStatusReq {
    private List<GridLayout.Spec> records;

    public List<GridLayout.Spec> getRecords() {
        return records;
    }

    public void setRecords(List<GridLayout.Spec> records) {
        this.records = records;
    }

    public static class SpecsStatusItem {
        private String productID;
        private String specID;
        /**
         * 规格状态(4-上架，5-下架)
         */
        private String specStatus;

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getSpecID() {
            return specID;
        }

        public void setSpecID(String specID) {
            this.specID = specID;
        }

        public String getSpecStatus() {
            return specStatus;
        }

        public void setSpecStatus(String specStatus) {
            this.specStatus = specStatus;
        }
    }
}


