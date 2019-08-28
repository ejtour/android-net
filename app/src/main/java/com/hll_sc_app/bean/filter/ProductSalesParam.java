package com.hll_sc_app.bean.filter;

import com.hll_sc_app.citymall.util.CalendarUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/24
 */

public class ProductSalesParam extends DateParam {
    private int dateFlag = 1;
    private int type = 1;

    public String getFormatStartDate() {
        return dateFlag != 4 || getStartDate() == null ? null : CalendarUtils.toLocalDate(getStartDate());
    }

    public String getFormatEndDate() {
        return dateFlag != 4 || getEndDate() == null ? null : CalendarUtils.toLocalDate(getEndDate());
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
