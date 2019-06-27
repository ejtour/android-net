package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品模板查询
 *
 * @author zhuyingsong
 * @date 2019-06-27
 */
public class GoodsTemplateResp {
    private int totalSize;
    private List<GoodsBean> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<GoodsBean> getRecords() {
        return records;
    }

    public void setRecords(List<GoodsBean> records) {
        this.records = records;
    }
}
