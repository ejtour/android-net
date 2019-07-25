package com.hll_sc_app.bean.report.resp.product;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品销售统计汇总响应参数
 */

public class ProductSaleAggregationResp {

    private double orderAmount;
    private long   orderNum;
    private long   skuNum;
    private List<ProductCategory> productCategorySaleVo  = new ArrayList<>();

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(long skuNum) {
        this.skuNum = skuNum;
    }

    public List<ProductCategory> getProductCategorySaleVo() {
        return productCategorySaleVo;
    }

    public void setProductCategorySaleVo(List<ProductCategory> productCategorySaleVo) {
        this.productCategorySaleVo = productCategorySaleVo;
    }
}
