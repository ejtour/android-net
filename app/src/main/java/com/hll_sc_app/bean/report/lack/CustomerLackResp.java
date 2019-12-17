package com.hll_sc_app.bean.report.lack;

import java.util.ArrayList;
import java.util.List;

public class CustomerLackResp {

    private List<CustomerLackDetailsBean> detail = new ArrayList<>();

    private List<CustomerLackBean> summary = new ArrayList<>();

    private int totalSize;

    public List<CustomerLackDetailsBean> getDetail() {
        return detail;
    }

    public void setDetail(List<CustomerLackDetailsBean> detail) {
        this.detail = detail;
    }

    public List<CustomerLackBean> getSummary() {
        return summary;
    }

    public void setSummary(List<CustomerLackBean> summary) {
        this.summary = summary;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
