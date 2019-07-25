package com.hll_sc_app.bean.report.resp.product;

import java.util.List;

/**
 * 商品销售统计汇总响应参数
 */

public class ProductSaleResp {

    private double orderAmount;
    private double orderNum;
    private double skuNum;
    private List<ProductCategory> productCategorySaleVo;

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

    public double getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(double skuNum) {
        this.skuNum = skuNum;
    }

    public List<ProductCategory> getProductCategorySaleVo() {
        return productCategorySaleVo;
    }

    public void setProductCategorySaleVo(List<ProductCategory> productCategorySaleVo) {
        this.productCategorySaleVo = productCategorySaleVo;
    }
}
