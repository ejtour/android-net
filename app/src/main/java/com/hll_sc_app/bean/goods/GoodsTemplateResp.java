package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品模板查询
 *
 * @author zhuyingsong
 * @date 2019-06-27
 */
public class GoodsTemplateResp {
    private int total;
    private List<GoodsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GoodsBean> getRecords() {
        return records;
    }

    public void setRecords(List<GoodsBean> records) {
        this.records = records;
    }
}
