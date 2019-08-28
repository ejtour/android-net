package com.hll_sc_app.bean.report.refund;

public class RefundedReq {

    /**
     * 结束日期 必传
     */
    private String endDate;
    /**
     * 供应商ID 必传
     */
    private String groupID;
    /**
     * 页码 必传
     */
    private int    pageNum;
    /**
     * 页长 必传
     */
    private int    pageSize;
    /**
     * 是否包含押金 1-包含 2-不包含 必传
     */
    private int    sign;
    /**
     * 	开始日期 必传
     */
    private String startDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
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

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
