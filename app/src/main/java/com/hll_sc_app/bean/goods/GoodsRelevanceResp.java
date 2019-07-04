package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 关联商品 Bean
 *
 * @author zhuyingsong
 * @date 2019-07-04
 */
public class GoodsRelevanceResp {
    private int total;
    private List<GoodsRelevanceBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GoodsRelevanceBean> getRecords() {
        return records;
    }

    public void setRecords(List<GoodsRelevanceBean> records) {
        this.records = records;
    }
}
