package com.hll_sc_app.bean.report.resp.product;

import java.util.List;

/**
 * 商品销售统计top10响应参数
 */
public class ProductSaleTop10Resp {

    private List<ProductSaleTop10Bean> records;

    public List<ProductSaleTop10Bean> getRecords() {
        return records;
    }

    public void setRecords(List<ProductSaleTop10Bean> records) {
        this.records = records;
    }
}
