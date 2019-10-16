package com.hll_sc_app.bean.operationanalysis;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class AnalysisParam {
    private int timeType; // 2：周，3：月
    private Date date;

    public AnalysisParam() {
        this.timeType = 2;
        this.date = CalendarUtils.getWeekDate(-1, 1);
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatDate() {
        return date == null ? "" : CalendarUtils.toLocalDate(date);
    }
}
