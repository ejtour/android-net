package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 查询商品关联的集团列表
 *
 * @author zhuyingsong
 * @date 2019-07-04
 */
public class RelevancePurchaserResp {
    private int total;
    private List<PurchaserBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PurchaserBean> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaserBean> records) {
        this.records = records;
    }
}
