package com.hll_sc_app.bean.report.refund;

public class WaitRefundProductItem {

    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品ID
     */
    private long   productID;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 规格内容
     */
    private String productSpec;
    /**
     * 规格ID
     */
    private long   productSpecID;
    /**
     * 退款金额
     */
    private String refundAmount;
    /**
     * 退货单数
     */
    private String refundNum;
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

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
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

    public long getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(long productSpecID) {
        this.productSpecID = productSpecID;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(String refundNum) {
        this.refundNum = refundNum;
    }

    public String getRefundUnit() {
        return refundUnit;
    }

    public void setRefundUnit(String refundUnit) {
        this.refundUnit = refundUnit;
    }
}
