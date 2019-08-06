package com.hll_sc_app.bean.wallet.details;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public class WalletDetailsParam {
    private Date beginTime;
    private Date endTime;
    private String settleUnitID;
    private boolean isFilter;
    private boolean isRange;

    public String getFormatBeginTime() {
        return beginTime == null ? null : CalendarUtils.toLocalDate(beginTime);
    }

    public String getFormatEndTime() {
        return endTime == null ? null : CalendarUtils.toLocalDate(CalendarUtils.getDateAfter(endTime, 1));
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}
