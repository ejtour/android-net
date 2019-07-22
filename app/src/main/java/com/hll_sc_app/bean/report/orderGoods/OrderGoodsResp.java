package com.hll_sc_app.bean.report.orderGoods;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsResp {
    public static class PageBean {
        private int pageCount;
        private int pageNo;
        private int pageSize;
        private int totalSize;
    }

    private PageBean page;
    private List<OrderGoodsBean> records;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<OrderGoodsBean> getRecords() {
        return records;
    }

    public void setRecords(List<OrderGoodsBean> records) {
        this.records = records;
    }
}
