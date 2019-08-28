package com.hll_sc_app.bean.filter;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class DateParam {
    private Date startDate;
    private Date endDate;

    public String getFormatStartDate() {
        return getFormatStartDate(Constants.UNSIGNED_YYYY_MM_DD);
    }

    public String getFormatEndDate() {
        return getFormatEndDate(Constants.UNSIGNED_YYYY_MM_DD);
    }

    public String getFormatStartDate(String format) {
        return startDate == null ? null : CalendarUtils.format(startDate, format);
    }

    public String getFormatEndDate(String format) {
        return endDate == null ? null : CalendarUtils.format(endDate, format);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
