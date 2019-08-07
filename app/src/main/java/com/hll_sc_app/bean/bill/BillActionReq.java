package com.hll_sc_app.bean.bill;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillActionReq {
    private List<Integer> settleBillIDs;

    public List<Integer> getSettleBillIDs() {
        return settleBillIDs;
    }

    public void setSettleBillIDs(List<Integer> settleBillIDs) {
        this.settleBillIDs = settleBillIDs;
    }
}
