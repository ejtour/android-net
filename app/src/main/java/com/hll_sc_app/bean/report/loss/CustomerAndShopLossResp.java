package com.hll_sc_app.bean.report.loss;

import java.util.List;

public class CustomerAndShopLossResp {

    private int  totalSize;
    private List<CustomerAndShopLossItem> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CustomerAndShopLossItem> getRecords() {
        return records;
    }

    public void setRecords(List<CustomerAndShopLossItem> records) {
        this.records = records;
    }
}
