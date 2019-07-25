package com.hll_sc_app.bean.report.resp.product;

public class ProductSaleTop10Bean {

    /**
     * 毛利
     */
    private double grossProfit;
    /**
     * 毛利率
     */
    private double grossProfitRate;
    /**
     * 金额
     */
    private double orderAmount;
    /**
     * 销量
     */
    private double orderNum;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 规格
     */
    private String productSpec;


    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getGrossProfitRate() {
        return grossProfitRate;
    }

    public void setGrossProfitRate(double grossProfitRate) {
        this.grossProfitRate = grossProfitRate;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(double orderNum) {
        this.orderNum = orderNum;
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
}
