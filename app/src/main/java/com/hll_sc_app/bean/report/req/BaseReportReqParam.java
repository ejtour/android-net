package com.hll_sc_app.bean.report.req;


public class BaseReportReqParam {

    /**
     * 供应商集团ID
     */
    private String groupID;
    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 页码，不传默认第一页
     */
    private int  pageNum = 1;
    /**
     * 每页数量，不传默认10条
     */
    private int  pageSize = 10;
    /**
     * 2：本周 4：本月 5：上月 6：自定义
     */
    private int  timeFlag;
    /**
     * 0：升序，1：降序
     */
    private int  order;
    /**
     * 排序字段
     */
    private int  sortBy;

    /**
     * 1:日，2：周，3：月
     */
    private int  timeType;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public int getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(int timeFlag) {
        this.timeFlag = timeFlag;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }
}
