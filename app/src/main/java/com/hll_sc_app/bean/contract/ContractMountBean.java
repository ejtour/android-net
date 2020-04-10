package com.hll_sc_app.bean.contract;

public class ContractMountBean {
    private String contractTotalAmount;
    private String lessContractAmount;
    private String orderCount;
    private String orderTotalAmount;
    private String productCount;
    private String shopCount;

    public String getContractTotalAmount() {
        return contractTotalAmount;
    }

    public void setContractTotalAmount(String contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }

    public String getLessContractAmount() {
        return lessContractAmount;
    }

    public void setLessContractAmount(String lessContractAmount) {
        this.lessContractAmount = lessContractAmount;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(String orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getShopCount() {
        return shopCount;
    }

    public void setShopCount(String shopCount) {
        this.shopCount = shopCount;
    }
}
