package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoiceListResp {
    private List<InvoiceBean> records;
    private int total;

    public List<InvoiceBean> getRecords() {
        return records;
    }

    public void setRecords(List<InvoiceBean> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
