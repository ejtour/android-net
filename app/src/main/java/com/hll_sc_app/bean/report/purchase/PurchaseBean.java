package com.hll_sc_app.bean.report.purchase;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class PurchaseBean {
    private String date;
    private int purchaserNum;
    private double logisticsCost;
    private int carNums;
    private String id;
    private double purchaserEfficiency;
    private double purchaseAmount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPurchaserNum() {
        return purchaserNum;
    }

    public void setPurchaserNum(int purchaserNum) {
        this.purchaserNum = purchaserNum;
    }

    public double getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(double logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public int getCarNums() {
        return carNums;
    }

    public void setCarNums(int carNums) {
        this.carNums = carNums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPurchaserEfficiency() {
        return purchaserEfficiency;
    }

    public void setPurchaserEfficiency(double purchaserEfficiency) {
        this.purchaserEfficiency = purchaserEfficiency;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }
}
