package com.hll_sc_app.bean.report.daily;

/**
 * 销售日报明细
 */
public class SalesDailyDetailBean {

    /**
     * 日报日期
     */
    private String reportDate;
    /**
     * 日报个数
     */
    private int reportNum;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }
}
