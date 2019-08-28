package com.hll_sc_app.bean.report.refund;

public class RefundedCustomerReq {

    /**
     * 结束日期 必传
     */
    private String endDate;
    /**
     * 供应商ID 必传
     */
    private String groupID;
    /**
     * 0:升序 1：降序 默认0
     */
    private int    order;
    /**
     * 页码 必传
     */
    private int    pageNum;
    /**
     * 页长 必传
     */
    private int    pageSize;
    /**
     * 采购商集团列表
     */
    private String purchaserID;
    /**
     * 采购商门店列表
     */
    private String shopID;
    /**
     * 是否包含押金 1-包含 2-不包含 必传
     */
    private int    sign;
    /**
     * 0-退单数、2-退货金额 默认0
     */
    private int    sortBy;
    /**
     * 开始日期 必传
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
