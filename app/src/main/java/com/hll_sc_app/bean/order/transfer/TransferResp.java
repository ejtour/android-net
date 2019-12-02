package com.hll_sc_app.bean.order.transfer;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/18
 */

public class TransferResp {
    private int unReceiveTotal;
    private int total;
    private List<TransferBean> records;

    public int getUnReceiveTotal() {
        return unReceiveTotal;
    }

    public void setUnReceiveTotal(int unReceiveTotal) {
        this.unReceiveTotal = unReceiveTotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TransferBean> getRecords() {
        return records;
    }

    public void setRecords(List<TransferBean> records) {
        this.records = records;
    }
}
