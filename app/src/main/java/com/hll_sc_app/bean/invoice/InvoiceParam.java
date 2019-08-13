package com.hll_sc_app.bean.invoice;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoiceParam {
    private Date startTime;
    private Date endTime;

    public String getFormatStartTime() {
        return startTime == null ? null : CalendarUtils.toLocalDate(startTime);
    }

    public String getFormatEndTime() {
        return endTime == null ? null : CalendarUtils.toLocalDate(endTime);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
