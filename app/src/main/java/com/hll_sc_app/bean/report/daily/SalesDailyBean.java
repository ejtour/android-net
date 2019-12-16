package com.hll_sc_app.bean.report.daily;

import java.util.List;

/**
 * 销售日报记录
 */
public class SalesDailyBean {

    /**
     * 日报个数
     */
    private int reportNum;
    /**
     * 销售名称
     */
    private String salesmanName;
    /**
     * 日报详情
     */
    private List<SalesDailyDetailBean> reportDetail;

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public List<SalesDailyDetailBean> getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(List<SalesDailyDetailBean> reportDetail) {
        this.reportDetail = reportDetail;
    }
}
