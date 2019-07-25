package com.hll_sc_app.bean.report.resp.product;

/**
 * 客户订货明细响应参数
 */
public class CustomerOrderDetail {

    private String inspectionAmount;
    private String inspectionNum;
    private byte   nextDayDelivery;
    private String orderAmount;
    private int    orderCount;
    private String orderNum;
    private String productCode;
    private String productName;
    private long productSpecID;
    private String spec;
    private String unit;

    public String getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(String inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public String getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(String inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public byte getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(byte nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

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

    public long getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(long productSpecID) {
        this.productSpecID = productSpecID;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
