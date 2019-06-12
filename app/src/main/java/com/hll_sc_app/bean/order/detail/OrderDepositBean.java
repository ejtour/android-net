package com.hll_sc_app.bean.order.detail;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public class OrderDepositBean {
    private double depositNum;
    private int detailID;
    private int productID;
    private String productName;
    private double productNum;
    private double productPrice;
    private String productSpec;
    private int productSpecID;
    private double replenishmentNum;
    private String saleUnitName;
    private double subtotalAmount;

    public double getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(double depositNum) {
        this.depositNum = depositNum;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public int getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(int productSpecID) {
        this.productSpecID = productSpecID;
    }

    public double getReplenishmentNum() {
        return replenishmentNum;
    }

    public void setReplenishmentNum(double replenishmentNum) {
        this.replenishmentNum = replenishmentNum;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public double getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }
}
