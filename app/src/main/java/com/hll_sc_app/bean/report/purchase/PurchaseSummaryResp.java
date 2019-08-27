package com.hll_sc_app.bean.report.purchase;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class PurchaseSummaryResp {
    private int totalCarNums;
    private double totalLogisticsCost;
    private double totalPurchaseAmount;
    private double totalPurchaserEfficiency;
    private int totalPurchaserNum;
    private int totalSize;
    private List<PurchaseBean> records;

    public int getTotalCarNums() {
        return totalCarNums;
    }

    public void setTotalCarNums(int totalCarNums) {
        this.totalCarNums = totalCarNums;
    }

    public double getTotalLogisticsCost() {
        return totalLogisticsCost;
    }

    public void setTotalLogisticsCost(double totalLogisticsCost) {
        this.totalLogisticsCost = totalLogisticsCost;
    }

    public double getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(double totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public double getTotalPurchaserEfficiency() {
        return totalPurchaserEfficiency;
    }

    public void setTotalPurchaserEfficiency(double totalPurchaserEfficiency) {
        this.totalPurchaserEfficiency = totalPurchaserEfficiency;
    }

    public int getTotalPurchaserNum() {
        return totalPurchaserNum;
    }

    public void setTotalPurchaserNum(int totalPurchaserNum) {
        this.totalPurchaserNum = totalPurchaserNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<PurchaseBean> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaseBean> records) {
        this.records = records;
    }
}
