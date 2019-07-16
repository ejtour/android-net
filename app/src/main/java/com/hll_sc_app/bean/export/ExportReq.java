package com.hll_sc_app.bean.export;

import java.util.List;

/**
 * 统一导出文件请求参数
 *
 * @author zhuyingsong
 * @since 2019/7/3
 */

public class ExportReq {
    /**
     * 1.直接导出2.发送邮件3.查询服务器文件地址
     */
    private String actionType = "2";
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 是否绑定邮箱 0.否1.是
     */
    private String isBindEmail;
    /**
     * 请求参数
     */
    private ParamsBean params;
    /**
     * 导出投诉列表(complaint_info)
     * 导出售价变更日志（priceFlow）
     * 导出协议价列表（quotation）
     * 代仓商品导出（warehouse_product）
     * 平台分类（category）
     * 库存流水（stock_flow）
     * 规格上下记录导出（skuShelfFlow），
     * 供应商常采清单（supplier_common_export）
     * 合作关系（cooperation）
     * 税率（taxRate）
     * 商品价格（product_price）
     */
    private String typeCode;
    /**
     * 用户ID
     */
    private String userID;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(String isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public static class ParamsBean {
        /**
         * 规格上下架日志导出
         */
        private SkuShelfFlowBean skuShelfFlow;
        /**
         * 协议价请求参数
         */
        private SupplierQuotationBean supplierQuotation;
        /**
         * 售价变更请求参数
         */
        private PriceFlowBean priceFlow;

        public PriceFlowBean getPriceFlow() {
            return priceFlow;
        }

        public void setPriceFlow(PriceFlowBean priceFlow) {
            this.priceFlow = priceFlow;
        }

        public SupplierQuotationBean getSupplierQuotation() {
            return supplierQuotation;
        }

        public void setSupplierQuotation(SupplierQuotationBean supplierQuotation) {
            this.supplierQuotation = supplierQuotation;
        }

        public SkuShelfFlowBean getSkuShelfFlow() {
            return skuShelfFlow;
        }

        public void setSkuShelfFlow(SkuShelfFlowBean skuShelfFlow) {
            this.skuShelfFlow = skuShelfFlow;
        }

        public static class SkuShelfFlowBean {
            private String groupID;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }
        }

        public static class SupplierQuotationBean {
            private String groupID;
            private List<String> billNos;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }

            public List<String> getBillNos() {
                return billNos;
            }

            public void setBillNos(List<String> billNos) {
                this.billNos = billNos;
            }
        }

        public static class PriceFlowBean {
            private String groupID;
            private String endTime;
            private String startTime;
            private String productName;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }
    }
}
