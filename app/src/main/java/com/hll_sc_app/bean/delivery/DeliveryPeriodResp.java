package com.hll_sc_app.bean.delivery;

import java.util.List;

/**
 * 配送时段列表查询
 *
 * @author zhuyingsong
 * @date 2019-07-19
 */
public class DeliveryPeriodResp {
    private List<DeliveryPeriodBean> records;

    public List<DeliveryPeriodBean> getRecords() {
        return records;
    }

    public void setRecords(List<DeliveryPeriodBean> records) {
        this.records = records;
    }
}
