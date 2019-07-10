package com.hll_sc_app.bean.agreementprice.quotation;

/**
 * 分页数据
 *
 * @author zhuyingsong
 * @date 2019-07-10
 */
public class PageInfoBean {
    private int total;
    private int pages;
    private int pageSize;
    private int pageNum;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
