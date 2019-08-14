package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public class InvoiceHistoryResp {
    private List<InvoiceHistoryBean> records;
    private int total;

    public List<InvoiceHistoryBean> getRecords() {
        return records;
    }

    public void setRecords(List<InvoiceHistoryBean> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
