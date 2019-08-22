package com.hll_sc_app.bean.report.warehouse;

import java.util.ArrayList;
import java.util.List;

public class WareHouseLackProductResp {

    private String totalDeliveryLackAmount;
    private String totalDeliveryLackRate;
    private int totalSize;
    private List<WareHouseLackProductItem> records = new ArrayList<>();

    public String getTotalDeliveryLackAmount() {
        return totalDeliveryLackAmount;
    }

    public void setTotalDeliveryLackAmount(String totalDeliveryLackAmount) {
        this.totalDeliveryLackAmount = totalDeliveryLackAmount;
    }

    public String getTotalDeliveryLackRate() {
        return totalDeliveryLackRate;
    }

    public void setTotalDeliveryLackRate(String totalDeliveryLackRate) {
        this.totalDeliveryLackRate = totalDeliveryLackRate;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<WareHouseLackProductItem> getRecords() {
        return records;
    }

    public void setRecords(List<WareHouseLackProductItem> records) {
        this.records = records;
    }
}
