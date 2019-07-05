package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 押金商品列表数据返回
 *
 * @author zhuyingsong
 * @date 2019-06-20
 */
public class DepositProductsResp {
    private int total;
    private List<SKUGoodsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SKUGoodsBean> getRecords() {
        return records;
    }

    public void setRecords(List<SKUGoodsBean> records) {
        this.records = records;
    }
}
