package com.hll_sc_app.bean.stockmanage.purchaserorder;

import java.util.List;

/**
 * @author chukun
 * 采购单明细响应；列表
 */
public class PurchaserOrderDetailResp {

    private PurchaserOrderBean record;

    private List<PurchaserOrderDetailBean> records;

    public PurchaserOrderBean getRecord() {
        return record;
    }

    public void setRecord(PurchaserOrderBean record) {
        this.record = record;
    }

    public List<PurchaserOrderDetailBean> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaserOrderDetailBean> records) {
        this.records = records;
    }
}
