package com.hll_sc_app.bean.report.inspectLack.detail;

import com.hll_sc_app.bean.report.inspectLack.InspectLackItem;

import java.util.ArrayList;
import java.util.List;

public class InspectLackDetailResp {

    private String totalInspectionLackAmount;
    private String totalInspectionLackRate;
    private int totalSize;
    private List<InspectLackDetailItem> records = new ArrayList<>();


    public String getTotalInspectionLackAmount() {
        return totalInspectionLackAmount;
    }

    public void setTotalInspectionLackAmount(String totalInspectionLackAmount) {
        this.totalInspectionLackAmount = totalInspectionLackAmount;
    }

    public String getTotalInspectionLackRate() {
        return totalInspectionLackRate;
    }

    public void setTotalInspectionLackRate(String totalInspectionLackRate) {
        this.totalInspectionLackRate = totalInspectionLackRate;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<InspectLackDetailItem> getRecords() {
        return records;
    }

    public void setRecords(List<InspectLackDetailItem> records) {
        this.records = records;
    }
}
