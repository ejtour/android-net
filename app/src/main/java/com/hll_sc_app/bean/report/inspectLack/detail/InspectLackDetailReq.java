package com.hll_sc_app.bean.report.inspectLack.detail;

public class InspectLackDetailReq {

    private String date;
    private String groupID;
    private int order;
    private int    pageNum;
    private int    pageSize;
    private String productName;
    private int    sortBy;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }
}
