package com.hll_sc_app.bean.bill;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillActionReq {
    private List<String> settleBillIDs;

    public List<String> getSettleBillIDs() {
        return settleBillIDs;
    }

    public void setSettleBillIDs(List<String> settleBillIDs) {
        this.settleBillIDs = settleBillIDs;
    }
}
