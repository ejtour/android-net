package com.hll_sc_app.bean.stock.purchaserorder;

import java.util.List;

/**
 * @author chukun
 * 采购单明细响应；列表
 */
public class PurchaserOrderDetailResp {

    private PurchaserOrderRecord record;

    private List<PurchaserOrderDetailRecord> records;

    public PurchaserOrderRecord getRecord() {
        return record;
    }

    public void setRecord(PurchaserOrderRecord record) {
        this.record = record;
    }

    public List<PurchaserOrderDetailRecord> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaserOrderDetailRecord> records) {
        this.records = records;
    }
}
