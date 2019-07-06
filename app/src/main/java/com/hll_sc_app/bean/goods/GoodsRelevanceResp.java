package com.hll_sc_app.bean.goods;

import com.hll_sc_app.bean.order.detail.TransferDetailBean;

import java.util.List;

/**
 * 关联商品 Bean
 *
 * @author zhuyingsong
 * @date 2019-07-04
 */
public class GoodsRelevanceResp {
    private int total;
    private List<TransferDetailBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TransferDetailBean> getRecords() {
        return records;
    }

    public void setRecords(List<TransferDetailBean> records) {
        this.records = records;
    }
}
