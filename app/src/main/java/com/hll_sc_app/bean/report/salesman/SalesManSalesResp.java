package com.hll_sc_app.bean.report.salesman;

import java.util.List;

/**
 * 业务员销售额响应列表
 */
public class SalesManSalesResp {

    private double totalRefundAmount;
    private double totalSalesAmount;
    private double totalSettleAmount;
    private int totalSettleBillNum;
    private int totalValidBillNum;
    private int totalSize;

    private List<SalesManSalesAchievement> records;

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public double getTotalSettleAmount() {
        return totalSettleAmount;
    }

    public void setTotalSettleAmount(double totalSettleAmount) {
        this.totalSettleAmount = totalSettleAmount;
    }

    public int getTotalSettleBillNum() {
        return totalSettleBillNum;
    }

    public void setTotalSettleBillNum(int totalSettleBillNum) {
        this.totalSettleBillNum = totalSettleBillNum;
    }

    public int getTotalValidBillNum() {
        return totalValidBillNum;
    }

    public void setTotalValidBillNum(int totalValidBillNum) {
        this.totalValidBillNum = totalValidBillNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<SalesManSalesAchievement> getRecords() {
        return records;
    }

    public void setRecords(List<SalesManSalesAchievement> records) {
        this.records = records;
    }
}
