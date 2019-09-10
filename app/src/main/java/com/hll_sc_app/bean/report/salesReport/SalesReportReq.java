package com.hll_sc_app.bean.report.salesReport;

/**
 * 销售日报查询参数
 */
public class SalesReportReq {

    /**
     * yyyyMMdd（timeType为2时，传周的周一日期 timeType为3时，传月份的1号日期
     */
    private String date;
    /**
     * 	供应商集团ID
     */
    private String   groupID;
    /**
     * 关键字（员工名称、员工编码、手机号）
     */
    private String keyword;
    /**
     * 	页码，不传默认第一页
     */
    private int    pageNum;
    /**
     * 每页数量，不传默认10条
     */
    private int    pageSize;

    /**
     * 2：周，3：月
     */
    private int    timeType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }
}
