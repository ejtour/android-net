package com.hll_sc_app.bean.report.refund;

public class RefundedProductItem {

    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品规格
     */
    private String productSpec;
    /**
     * 退款
     */
    private String refundAmount;
    /**
     * 数量
     */
    private String refundProductNum;
    /**
     * 单位
     */
    private String refundUnit;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(String refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public String getRefundUnit() {
        return refundUnit;
    }

    public void setRefundUnit(String refundUnit) {
        this.refundUnit = refundUnit;
    }
}
