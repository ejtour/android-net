package com.hll_sc_app.bean.contract;

import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

//合同里的商品
public class ContractProductListResp {
    private List<ProduceBean> list;
    public List<ProduceBean> getList() {
        return list;
    }

    public void setList(List<ProduceBean> list) {
        this.list = list;
    }


    private static class ProduceBean implements IStringArrayGenerator {
        private String productCode;
        private String productID;
        private String productName;
        private String productNum;
        private String productSpecID;
        private String remarks;
        private String saleUnitName;
        private String specContent;

        @Override
        public List<CharSequence> convertToRowData() {
            List<CharSequence> list = new ArrayList<>();
            list.add(productCode);
            list.add(productID);
            list.add(productName);
            list.add(productNum);
            list.add(productSpecID);
            list.add(remarks);
            list.add(saleUnitName);
            list.add(specContent);
            return list;
        }

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

        public String getProductNum() {
            return productNum;
        }

        public void setProductNum(String productNum) {
            this.productNum = productNum;
        }

        public String getProductSpecID() {
            return productSpecID;
        }

        public void setProductSpecID(String productSpecID) {
            this.productSpecID = productSpecID;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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
