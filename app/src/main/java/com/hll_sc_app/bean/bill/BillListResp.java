package com.hll_sc_app.bean.bill;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */

public class BillListResp {
    private double totalNoSettlementAmount;
    private double totalSettlementAmount;
    private String totalCount;
    private List<BillBean> records;

    public double getTotalNoSettlementAmount() {
        return totalNoSettlementAmount;
    }

    public void setTotalNoSettlementAmount(double totalNoSettlementAmount) {
        this.totalNoSettlementAmount = totalNoSettlementAmount;
    }

    public double getTotalSettlementAmount() {
        return totalSettlementAmount;
    }

    public void setTotalSettlementAmount(double totalSettlementAmount) {
        this.totalSettlementAmount = totalSettlementAmount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<BillBean> getRecords() {
        return records;
    }

    public void setRecords(List<BillBean> records) {
        this.records = records;
    }
}
