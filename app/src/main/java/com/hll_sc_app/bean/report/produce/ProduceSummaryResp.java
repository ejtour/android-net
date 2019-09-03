package com.hll_sc_app.bean.report.produce;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public class ProduceSummaryResp {
    private List<ProduceBean> list;
    private ProduceTotal total;
    private int totalSize;

    public List<ProduceBean> getList() {
        return list;
    }

    public void setList(List<ProduceBean> list) {
        this.list = list;
    }

    public ProduceTotal getTotal() {
        return total;
    }

    public void setTotal(ProduceTotal total) {
        this.total = total;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
