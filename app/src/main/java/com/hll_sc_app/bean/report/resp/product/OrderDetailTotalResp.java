package com.hll_sc_app.bean.report.resp.product;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailTotalResp {

    private long skuNum;
    private String totalInspectionAmount;
    private String totalInspectionNum;
    private int totalSize;
    private List<CustomerOrderDetail> records = new ArrayList<>();

    public long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(long skuNum) {
        this.skuNum = skuNum;
    }

    public String getTotalInspectionAmount() {
        return totalInspectionAmount;
    }

    public void setTotalInspectionAmount(String totalInspectionAmount) {
        this.totalInspectionAmount = totalInspectionAmount;
    }

    public String getTotalInspectionNum() {
        return totalInspectionNum;
    }

    public void setTotalInspectionNum(String totalInspectionNum) {
        this.totalInspectionNum = totalInspectionNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CustomerOrderDetail> getRecords() {
        return records;
    }

    public void setRecords(List<CustomerOrderDetail> records) {
        this.records = records;
    }
}
