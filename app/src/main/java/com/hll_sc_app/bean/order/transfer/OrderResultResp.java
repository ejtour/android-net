package com.hll_sc_app.bean.order.transfer;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class OrderResultResp {
    private List<InventoryBean> records;

    public List<InventoryBean> getRecords() {
        return records;
    }

    public void setRecords(List<InventoryBean> records) {
        this.records = records;
    }
}
