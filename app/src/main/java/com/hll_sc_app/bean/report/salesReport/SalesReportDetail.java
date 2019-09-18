package com.hll_sc_app.bean.report.salesReport;

/**
 * 销售日报明细
 */
public class SalesReportDetail {

    /**
     * 日报日期
     */
    private long reportDate;
    /**
     * 日报个数
     */
    private int  reportNum;

    public long getReportDate() {
        return reportDate;
    }

    public void setReportDate(long reportDate) {
        this.reportDate = reportDate;
    }

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }
}
