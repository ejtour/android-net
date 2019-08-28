package com.hll_sc_app.bean.filter;

import com.hll_sc_app.citymall.util.CalendarUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public class WalletDetailsParam extends DateParam {
    private String settleUnitID;
    private String transType;
    private boolean isFilter;
    private boolean isRange;

    @Override
    public String getFormatEndDate() {
        return getEndDate() == null ? null : CalendarUtils.toLocalDate(CalendarUtils.getDateAfter(getEndDate(), 1));
    }

    public String getSettleUnitID() {
        return settleUnitID;
    }

    public void setSettleUnitID(String settleUnitID) {
        this.settleUnitID = settleUnitID;
    }

    public boolean isFilter() {
        return isFilter;
    }

    public void setFilter(boolean filter) {
        isFilter = filter;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean range) {
        isRange = range;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
