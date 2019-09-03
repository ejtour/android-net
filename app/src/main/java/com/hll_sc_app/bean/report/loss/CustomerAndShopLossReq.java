package com.hll_sc_app.bean.report.loss;

/**
 * 客户流失率，流失门店参数封装
 */
public class CustomerAndShopLossReq {

    /**
     * 	流失类型 0-近七天流失 1-近30天流失
     */
    private int    dataType;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 0-客户流失率 1-门店明细
     */
    private int    flag;
    /**
     * 集团ID
     */
    private String groupID;
    /**
     * 页码
     */
    private int    pageNum;
    /**
     * 每页条数
     */
    private int    pageSize;
    /**
     * 开始时间
     */
    private String startDate;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
