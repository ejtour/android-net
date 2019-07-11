package com.hll_sc_app.bean.agreementprice.quotation;

/**
 * 报价生效时间
 *
 * @author zhuyingsong
 * @date 2019-07-11
 */
public class TimeBean {
    private String endDate;
    private double price;
    private String activeTime;
    private int isActive;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
