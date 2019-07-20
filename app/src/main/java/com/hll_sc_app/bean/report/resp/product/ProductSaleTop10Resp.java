package com.hll_sc_app.bean.report.resp.product;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品销售统计top10响应参数
 */
public class ProductSaleTop10Resp {

   List<ProductSaleTopDetail> records = new ArrayList<>();

    public List<ProductSaleTopDetail> getRecords() {
        return records;
    }

    public void setRecords(List<ProductSaleTopDetail> records) {
        this.records = records;
    }
}
