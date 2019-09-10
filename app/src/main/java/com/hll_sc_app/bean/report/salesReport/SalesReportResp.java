package com.hll_sc_app.bean.report.salesReport;

import java.util.List;

/**
 * 销售日报响应数据
 */
public class SalesReportResp {

    /**
     * 总记录数
     */
    private int totalSize;
    private List<SalesReportRecord> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<SalesReportRecord> getRecords() {
        return records;
    }

    public void setRecords(List<SalesReportRecord> records) {
        this.records = records;
    }
}
