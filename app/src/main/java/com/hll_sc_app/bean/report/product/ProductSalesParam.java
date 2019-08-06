package com.hll_sc_app.bean.report.product;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/24
 */

public class ProductSalesParam {
    private Date startDate;
    private Date endDate;
    private int dateFlag = 1;
    private int type = 1;

    public String getFormatStartDate() {
        return dateFlag != 4 || startDate == null ? null : CalendarUtils.toLocalDate(startDate);
    }

    public String getFormatEndDate() {
        return dateFlag != 4 || endDate == null ? null : CalendarUtils.toLocalDate(endDate);
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

    public int getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(int dateFlag) {
        this.dateFlag = dateFlag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
