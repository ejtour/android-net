package com.hll_sc_app.bean.stockmanage.purchaserorder;

import java.util.List;

/**
 * 采购单响应数据
 */
public class PurchaserOrderResp {

    private PurchaserOrderPageInfo pageInfo;

    private List<PurchaserOrderRecord> records;


    public PurchaserOrderPageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PurchaserOrderPageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<PurchaserOrderRecord> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaserOrderRecord> records) {
        this.records = records;
    }
}
