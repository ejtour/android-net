package com.hll_sc_app.bean.report.salesman;

import com.hll_sc_app.bean.report.req.BaseReportReqParam;

/**
 * 业务员绩效请求参数
 */
public class SalesManAchievementReq extends BaseReportReqParam {

    /**
     * 时间
     */
    private String date;
    /**
     * 手机号,员工编号,业务员姓名
     */
    private String keyWords;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
