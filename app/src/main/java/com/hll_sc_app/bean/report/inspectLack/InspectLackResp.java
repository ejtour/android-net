package com.hll_sc_app.bean.report.inspectLack;

import java.util.ArrayList;
import java.util.List;

public class InspectLackResp {

    private String totalInspectionLackAmount;
    private String totalInspectionLackNum;
    private String totalInspectionLackRate;
    private String totalInspectionOrderNum;
    private String totalInspectionTotalAmount;
    private String totalOriDeliveryTradeAmount;
    private int totalSize;
    private List<InspectLackItem> records = new ArrayList<>();


    public String getTotalInspectionLackAmount() {
        return totalInspectionLackAmount;
    }

    public void setTotalInspectionLackAmount(String totalInspectionLackAmount) {
        this.totalInspectionLackAmount = totalInspectionLackAmount;
    }

    public String getTotalInspectionLackNum() {
        return totalInspectionLackNum;
    }

    public void setTotalInspectionLackNum(String totalInspectionLackNum) {
        this.totalInspectionLackNum = totalInspectionLackNum;
    }

    public String getTotalInspectionLackRate() {
        return totalInspectionLackRate;
    }

    public void setTotalInspectionLackRate(String totalInspectionLackRate) {
        this.totalInspectionLackRate = totalInspectionLackRate;
    }

    public String getTotalInspectionOrderNum() {
        return totalInspectionOrderNum;
    }

    public void setTotalInspectionOrderNum(String totalInspectionOrderNum) {
        this.totalInspectionOrderNum = totalInspectionOrderNum;
    }

    public String getTotalInspectionTotalAmount() {
        return totalInspectionTotalAmount;
    }

    public void setTotalInspectionTotalAmount(String totalInspectionTotalAmount) {
        this.totalInspectionTotalAmount = totalInspectionTotalAmount;
    }

    public String getTotalOriDeliveryTradeAmount() {
        return totalOriDeliveryTradeAmount;
    }

    public void setTotalOriDeliveryTradeAmount(String totalOriDeliveryTradeAmount) {
        this.totalOriDeliveryTradeAmount = totalOriDeliveryTradeAmount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<InspectLackItem> getRecords() {
        return records;
    }

    public void setRecords(List<InspectLackItem> records) {
        this.records = records;
    }
}
