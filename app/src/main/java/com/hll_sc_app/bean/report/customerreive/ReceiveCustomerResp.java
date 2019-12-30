package com.hll_sc_app.bean.report.customerreive;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class ReceiveCustomerResp {
    private String totalInAmount;
    private String totalOutAmount;
    private List<ReceiveCustomerBean> records;

    public String getTotalInAmount() {
        return totalInAmount;
    }

    public void setTotalInAmount(String totalInAmount) {
        this.totalInAmount = totalInAmount;
    }

    public String getTotalOutAmount() {
        return totalOutAmount;
    }

    public void setTotalOutAmount(String totalOutAmount) {
        this.totalOutAmount = totalOutAmount;
    }

    public List<ReceiveCustomerBean> getRecords() {
        return records;
    }

    public void setRecords(List<ReceiveCustomerBean> records) {
        this.records = records;
    }
}
