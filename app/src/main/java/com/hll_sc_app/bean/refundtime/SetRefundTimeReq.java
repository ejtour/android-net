package com.hll_sc_app.bean.refundtime;

import java.util.List;

public class SetRefundTimeReq {
    private Integer customerLevel;
    private Long groupID;
    private List<RefundTimeBean> refunds;

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public Long getGroupID() {
        return groupID;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public List<RefundTimeBean> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundTimeBean> refunds) {
        this.refunds = refunds;
    }
}
