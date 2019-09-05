package com.hll_sc_app.bean.order.shop;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public class OrderShopBean {
    private double allAmount;
    private int allBillNum;
    private String lastVisitTime;
    private String linkman;
    private String mobile;
    private String purchaserID;
    private String shopAddress;
    private String shopID;
    private String shopName;
    private double thirtyDaysAverageAmount;
    private double thirtyDaysAverageBillNum;
    private double thirtyDaysAmount;
    private int thirtyDaysBillNum;
    private double todayAmount;
    private int todayBillNum;

    public double getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(double allAmount) {
        this.allAmount = allAmount;
    }

    public int getAllBillNum() {
        return allBillNum;
    }

    public void setAllBillNum(int allBillNum) {
        this.allBillNum = allBillNum;
    }

    public String getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(String lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getThirtyDaysAverageAmount() {
        return thirtyDaysAverageAmount;
    }

    public void setThirtyDaysAverageAmount(double thirtyDaysAverageAmount) {
        this.thirtyDaysAverageAmount = thirtyDaysAverageAmount;
    }

    public double getThirtyDaysAverageBillNum() {
        return thirtyDaysAverageBillNum;
    }

    public void setThirtyDaysAverageBillNum(double thirtyDaysAverageBillNum) {
        this.thirtyDaysAverageBillNum = thirtyDaysAverageBillNum;
    }

    public double getThirtyDaysAmount() {
        return thirtyDaysAmount;
    }

    public void setThirtyDaysAmount(double thirtyDaysAmount) {
        this.thirtyDaysAmount = thirtyDaysAmount;
    }

    public int getThirtyDaysBillNum() {
        return thirtyDaysBillNum;
    }

    public void setThirtyDaysBillNum(int thirtyDaysBillNum) {
        this.thirtyDaysBillNum = thirtyDaysBillNum;
    }

    public double getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(double todayAmount) {
        this.todayAmount = todayAmount;
    }

    public int getTodayBillNum() {
        return todayBillNum;
    }

    public void setTodayBillNum(int todayBillNum) {
        this.todayBillNum = todayBillNum;
    }
}
