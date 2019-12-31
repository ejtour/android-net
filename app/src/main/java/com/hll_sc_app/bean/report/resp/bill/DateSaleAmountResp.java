package com.hll_sc_app.bean.report.resp.bill;

import java.util.List;

/**
 * 日销售额响应参数
 */
public class DateSaleAmountResp {
    private double totalAverageAmount;
    private int totalOrderNum;
    private double totalRefundAmount;
    private int totalRefundBillNum;
    private int totalSize;
    private double totalSubtotalAmount;
    private double totalTradeAmount;
    private int totalValidOrderNum;
    private List<DateSaleAmount> records;

    public double getTotalAverageAmount() {
        return totalAverageAmount;
    }

    public void setTotalAverageAmount(double totalAverageAmount) {
        this.totalAverageAmount = totalAverageAmount;
    }

    public int getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(int totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public int getTotalRefundBillNum() {
        return totalRefundBillNum;
    }

    public void setTotalRefundBillNum(int totalRefundBillNum) {
        this.totalRefundBillNum = totalRefundBillNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public double getTotalSubtotalAmount() {
        return totalSubtotalAmount;
    }

    public void setTotalSubtotalAmount(double totalSubtotalAmount) {
        this.totalSubtotalAmount = totalSubtotalAmount;
    }

    public double getTotalTradeAmount() {
        return totalTradeAmount;
    }

    public void setTotalTradeAmount(double totalTradeAmount) {
        this.totalTradeAmount = totalTradeAmount;
    }

    public int getTotalValidOrderNum() {
        return totalValidOrderNum;
    }

    public void setTotalValidOrderNum(int totalValidOrderNum) {
        this.totalValidOrderNum = totalValidOrderNum;
    }

    public List<DateSaleAmount> getRecords() {
        return records;
    }

    public void setRecords(List<DateSaleAmount> records) {
        this.records = records;
    }
}
