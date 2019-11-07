package com.hll_sc_app.bean.cardmanage;

import java.util.List;

public class CardManageListResp {
    private List<CardManageBean> records;
    private int total;

    public List<CardManageBean> getRecords() {
        return records;
    }

    public void setRecords(List<CardManageBean> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
