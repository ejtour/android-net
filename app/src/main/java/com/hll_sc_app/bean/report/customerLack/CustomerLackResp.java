package com.hll_sc_app.bean.report.customerLack;

import java.util.ArrayList;
import java.util.List;

public class CustomerLackResp {

    private List<CustomerLackItem> detail = new ArrayList<>();

    private List<CustomerLackSummary> summary = new ArrayList<>();

    private int totalSize;

    public List<CustomerLackItem> getDetail() {
        return detail;
    }

    public void setDetail(List<CustomerLackItem> detail) {
        this.detail = detail;
    }

    public List<CustomerLackSummary> getSummary() {
        return summary;
    }

    public void setSummary(List<CustomerLackSummary> summary) {
        this.summary = summary;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
