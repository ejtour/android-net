package com.hll_sc_app.bean.report.refund;

/**
 * 退货商品统计请求参数
 */
public class RefundedProductReq {

    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 供应商集团ID
     */
    private String groupID;
    /**
     * 0:升序 1：降序 默认0
     */
    private int    order;
    /**
     * 页码
     */
    private int    pageNum;
    /**
     * 默认20条
     */
    private int    pageSize;
    /**
     * 商品名称或者编码
     */
    private String productName;
    /**
     * 是否包含押金1-包含 2-不包含
     */
    private int    sign;
    /**
     * 0-商品编码、1-数量，2-退货金额 默认0
     */
    private int    sortBy;
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
