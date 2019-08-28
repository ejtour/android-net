package com.hll_sc_app.bean.filter;

import com.hll_sc_app.bean.bill.BillStatus;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillParam extends DateShopParam {
    private int settlementStatus;

    @BillStatus
    public int getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(@BillStatus int settlementStatus) {
        this.settlementStatus = settlementStatus;
    }
}
