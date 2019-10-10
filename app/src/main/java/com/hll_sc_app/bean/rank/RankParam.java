package com.hll_sc_app.bean.rank;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class RankParam {
    private int dateType;
    private Date startDate;

    public RankParam() {
        dateType = 1;
        startDate = new Date();
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getFormatDate() {
        return CalendarUtils.toLocalDate(startDate);
    }

    public String getFormatDate(String format) {
        return CalendarUtils.format(startDate, format);
    }
}
