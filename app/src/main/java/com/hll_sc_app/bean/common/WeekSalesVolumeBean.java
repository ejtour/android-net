package com.hll_sc_app.bean.common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/23
 */

public class WeekSalesVolumeBean {
    private String dayOfWeek;
    private float lastWeekTotalAmount;
    private float totalAmount;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public float getLastWeekTotalAmount() {
        return lastWeekTotalAmount;
    }

    public void setLastWeekTotalAmount(float lastWeekTotalAmount) {
        this.lastWeekTotalAmount = lastWeekTotalAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
