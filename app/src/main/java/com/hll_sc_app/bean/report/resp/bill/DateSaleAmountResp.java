package com.hll_sc_app.bean.report.resp.bill;

import java.util.ArrayList;
import java.util.List;

/**
 * 日销售额响应参数
 */
public class DateSaleAmountResp {
    private double totalAverageAmount;
    private long   totalOrderNum;
    private double totalRefundAmount;
    private long   totalRefundBillNum;
    private long   totalSize;
    private double totalSubtotalAmount;
    private double totalTradeAmount;
    private long   totalValidOrderNum;
    private List<DateSaleAmount> records = new ArrayList<>();

    public double getTotalAverageAmount() {
        return totalAverageAmount;
    }

    public void setTotalAverageAmount(double totalAverageAmount) {
        this.totalAverageAmount = totalAverageAmount;
    }

    public long getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(long totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public long getTotalRefundBillNum() {
        return totalRefundBillNum;
    }

    public void setTotalRefundBillNum(long totalRefundBillNum) {
        this.totalRefundBillNum = totalRefundBillNum;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
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

    public long getTotalValidOrderNum() {
        return totalValidOrderNum;
    }

    public void setTotalValidOrderNum(long totalValidOrderNum) {
        this.totalValidOrderNum = totalValidOrderNum;
    }

    public List<DateSaleAmount> getRecords() {
        return records;
    }

    public void setRecords(List<DateSaleAmount> records) {
        this.records = records;
    }
}
