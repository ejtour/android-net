package com.hll_sc_app.bean.operationanalysis;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class TopTenResp {
    private TopTenCustomerBean maxAmountActive;
    private TopTenCustomerBean maxAmountIncr;
    private TopTenCustomerBean maxOrderActive;
    private TopTenCustomerBean maxOrderIncr;
    private List<TopTenBean> records;

    public TopTenCustomerBean getMaxAmountActive() {
        return maxAmountActive;
    }

    public void setMaxAmountActive(TopTenCustomerBean maxAmountActive) {
        this.maxAmountActive = maxAmountActive;
    }

    public TopTenCustomerBean getMaxAmountIncr() {
        return maxAmountIncr;
    }

    public void setMaxAmountIncr(TopTenCustomerBean maxAmountIncr) {
        this.maxAmountIncr = maxAmountIncr;
    }

    public TopTenCustomerBean getMaxOrderActive() {
        return maxOrderActive;
    }

    public void setMaxOrderActive(TopTenCustomerBean maxOrderActive) {
        this.maxOrderActive = maxOrderActive;
    }

    public TopTenCustomerBean getMaxOrderIncr() {
        return maxOrderIncr;
    }

    public void setMaxOrderIncr(TopTenCustomerBean maxOrderIncr) {
        this.maxOrderIncr = maxOrderIncr;
    }

    public List<TopTenBean> getRecords() {
        return records;
    }

    public void setRecords(List<TopTenBean> records) {
        this.records = records;
    }
}
