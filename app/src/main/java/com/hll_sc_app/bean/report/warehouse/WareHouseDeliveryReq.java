package com.hll_sc_app.bean.report.warehouse;

import java.util.List;

/**
 * 代仓发货统计请求参数封装
 */
public class WareHouseDeliveryReq {

    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 供应商集团ID
     */
    private String groupID;
    /**
     * 页码
     */
    private int    pageNo;
    /**
     * 条数
     */
    private int    pageSize;
    /**
     * 货主集团IDs
     */
    private Long shipperGroupID;
    /**
     * 货主IDs
     */
    private List<Long> shipperShopIDList;
    /**
     * 开始日期
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

    public Long getShipperGroupID() {
        return shipperGroupID;
    }

    public void setShipperGroupID(Long shipperGroupID) {
        this.shipperGroupID = shipperGroupID;
    }

    public List<Long> getShipperShopIDList() {
        return shipperShopIDList;
    }

    public void setShipperShopIDList(List<Long> shipperShopIDList) {
        this.shipperShopIDList = shipperShopIDList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
