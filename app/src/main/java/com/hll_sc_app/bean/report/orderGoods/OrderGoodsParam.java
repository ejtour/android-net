package com.hll_sc_app.bean.report.orderGoods;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsParam {
    private Date startDate;
    private Date endDate;
    private String shopIDs;


    public String getFormatStartDate(){
        return startDate == null ? null : CalendarUtils.toLocalDate(startDate);
    }

    public String getFormatEndDate(){
        return endDate == null ? null : CalendarUtils.toLocalDate(endDate);
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

    public String getShopIDs() {
        return shopIDs;
    }

    public void setShopIDs(String shopIDs) {
        this.shopIDs = shopIDs;
    }
}
