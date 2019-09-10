package com.hll_sc_app.bean.stock.purchaserorder;

public class PurchaserOrderReq {

    /**
     * 结束日期 20170116
     */
    private String endDate;
    /**
     * 日期标志 1、订货日期 2、要求到货日期
     */
    private int    flag;
    /**
     * 供应商集团ID
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
     * 开始日期 20170116
     */
    private String startDate;
    /**
     * 供应链供应商ID 多个逗号连接
     */
    private String supplierIDs;


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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSupplierIDs() {
        return supplierIDs;
    }

    public void setSupplierIDs(String supplierIDs) {
        this.supplierIDs = supplierIDs;
    }
}
