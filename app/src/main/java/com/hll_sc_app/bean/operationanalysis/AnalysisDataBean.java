package com.hll_sc_app.bean.operationanalysis;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class AnalysisDataBean {
    private double dailyValidOrderNum;
    private double dailyValidTradeAmount;
    private int maxValidOrderNum;
    private String maxValidOrderNumTime;
    private double maxValidTradeAmount;
    private String maxValidTradeAmountTime;
    private int minValidOrderNum;
    private String minValidOrderNumTime;
    private double minValidTradeAmount;
    private String minValidTradeAmountTime;
    private double tbValidTradeAmount;

    public double getDailyValidOrderNum() {
        return dailyValidOrderNum;
    }

    public void setDailyValidOrderNum(double dailyValidOrderNum) {
        this.dailyValidOrderNum = dailyValidOrderNum;
    }

    public double getDailyValidTradeAmount() {
        return dailyValidTradeAmount;
    }

    public void setDailyValidTradeAmount(double dailyValidTradeAmount) {
        this.dailyValidTradeAmount = dailyValidTradeAmount;
    }

    public int getMaxValidOrderNum() {
        return maxValidOrderNum;
    }

    public void setMaxValidOrderNum(int maxValidOrderNum) {
        this.maxValidOrderNum = maxValidOrderNum;
    }

    public String getMaxValidOrderNumTime() {
        return maxValidOrderNumTime;
    }

    public void setMaxValidOrderNumTime(String maxValidOrderNumTime) {
        this.maxValidOrderNumTime = maxValidOrderNumTime;
    }

    public double getMaxValidTradeAmount() {
        return maxValidTradeAmount;
    }

    public void setMaxValidTradeAmount(double maxValidTradeAmount) {
        this.maxValidTradeAmount = maxValidTradeAmount;
    }

    public String getMaxValidTradeAmountTime() {
        return maxValidTradeAmountTime;
    }

    public void setMaxValidTradeAmountTime(String maxValidTradeAmountTime) {
        this.maxValidTradeAmountTime = maxValidTradeAmountTime;
    }

    public int getMinValidOrderNum() {
        return minValidOrderNum;
    }

    public void setMinValidOrderNum(int minValidOrderNum) {
        this.minValidOrderNum = minValidOrderNum;
    }

    public String getMinValidOrderNumTime() {
        return minValidOrderNumTime;
    }

    public void setMinValidOrderNumTime(String minValidOrderNumTime) {
        this.minValidOrderNumTime = minValidOrderNumTime;
    }

    public double getMinValidTradeAmount() {
        return minValidTradeAmount;
    }

    public void setMinValidTradeAmount(double minValidTradeAmount) {
        this.minValidTradeAmount = minValidTradeAmount;
    }

    public String getMinValidTradeAmountTime() {
        return minValidTradeAmountTime;
    }

    public void setMinValidTradeAmountTime(String minValidTradeAmountTime) {
        this.minValidTradeAmountTime = minValidTradeAmountTime;
    }

    public double getTbValidTradeAmount() {
        return tbValidTradeAmount;
    }

    public void setTbValidTradeAmount(double tbValidTradeAmount) {
        this.tbValidTradeAmount = tbValidTradeAmount;
    }
}
