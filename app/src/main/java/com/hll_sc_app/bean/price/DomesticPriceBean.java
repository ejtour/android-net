package com.hll_sc_app.bean.price;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public class DomesticPriceBean {
    private double averagePrice;
    private String farmProduceName;
    private double lastWeekAveragePrice;
    private int orderNum;
    private String riseFallRate;
    private double thisWeekAveragePrice;

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getFarmProduceName() {
        return farmProduceName;
    }

    public void setFarmProduceName(String farmProduceName) {
        this.farmProduceName = farmProduceName;
    }

    public double getLastWeekAveragePrice() {
        return lastWeekAveragePrice;
    }

    public void setLastWeekAveragePrice(double lastWeekAveragePrice) {
        this.lastWeekAveragePrice = lastWeekAveragePrice;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getRiseFallRate() {
        return riseFallRate;
    }

    public void setRiseFallRate(String riseFallRate) {
        this.riseFallRate = riseFallRate;
    }

    public double getThisWeekAveragePrice() {
        return thisWeekAveragePrice;
    }

    public void setThisWeekAveragePrice(double thisWeekAveragePrice) {
        this.thisWeekAveragePrice = thisWeekAveragePrice;
    }
}
