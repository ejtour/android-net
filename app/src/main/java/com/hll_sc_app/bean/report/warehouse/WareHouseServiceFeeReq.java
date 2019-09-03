package com.hll_sc_app.bean.report.warehouse;

/**
 * 代仓服务费参数
 */
public class WareHouseServiceFeeReq {

    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 集团ID
     */
    private String groupID;
    /**
     * 页码
     */
    private int    pageNo;
    /**
     * 页长
     */
    private int    pageSize;
    /**
     *  货主id
     */
    private long   shipperID;
    /**
     * 开始时间
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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getShipperID() {
        return shipperID;
    }

    public void setShipperID(long shipperID) {
        this.shipperID = shipperID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
