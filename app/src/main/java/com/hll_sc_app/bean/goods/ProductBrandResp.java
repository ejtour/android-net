package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品品牌
 *
 * @author zhuyingsong
 * @date 2019-06-24
 */
public class ProductBrandResp {
    private int totalSize;
    private List<ProductBrandBean> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ProductBrandBean> getRecords() {
        return records;
    }

    public void setRecords(List<ProductBrandBean> records) {
        this.records = records;
    }
}
