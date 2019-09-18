package com.hll_sc_app.bean.stockmanage.purchaserorder;

/**
 * 采购单响应分页信息
 */
public class PurchaserOrderPageInfo {

    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;


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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
