package com.hll_sc_app.bean.rank;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class SalesRankResp {
    private int groupAmountRank;
    private double validTradeAmount;
    private List<SalesRankBean> records;

    public int getGroupAmountRank() {
        return groupAmountRank;
    }

    public void setGroupAmountRank(int groupAmountRank) {
        this.groupAmountRank = groupAmountRank;
    }

    public double getValidTradeAmount() {
        return validTradeAmount;
    }

    public void setValidTradeAmount(double validTradeAmount) {
        this.validTradeAmount = validTradeAmount;
    }

    public List<SalesRankBean> getRecords() {
        return records;
    }

    public void setRecords(List<SalesRankBean> records) {
        this.records = records;
    }
}
