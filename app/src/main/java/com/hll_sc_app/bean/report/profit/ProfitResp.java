package com.hll_sc_app.bean.report.profit;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public class ProfitResp {
    private List<ProfitBean> records;
    private double totalOutAmount;
    private double totalProfitAmount;
    private String totalProfitRate;
    private double totalReceiveAmount;
    private int totalReserveNum;
    private int totalSize;
    private double totalUntaxReceiveAmount;
    private double unRefundTotalOutAmount;
    private double unRefundTotalProfitAmount;
    private String unRefundTotalProfitRate;

    public List<ProfitBean> getRecords() {
        return records;
    }

    public void setRecords(List<ProfitBean> records) {
        this.records = records;
    }

    public double getTotalOutAmount() {
        return totalOutAmount;
    }

    public void setTotalOutAmount(double totalOutAmount) {
        this.totalOutAmount = totalOutAmount;
    }

    public double getTotalProfitAmount() {
        return totalProfitAmount;
    }

    public void setTotalProfitAmount(double totalProfitAmount) {
        this.totalProfitAmount = totalProfitAmount;
    }

    public String getTotalProfitRate() {
        return totalProfitRate;
    }

    public void setTotalProfitRate(String totalProfitRate) {
        this.totalProfitRate = totalProfitRate;
    }

    public double getTotalReceiveAmount() {
        return totalReceiveAmount;
    }

    public void setTotalReceiveAmount(double totalReceiveAmount) {
        this.totalReceiveAmount = totalReceiveAmount;
    }

    public int getTotalReserveNum() {
        return totalReserveNum;
    }

    public void setTotalReserveNum(int totalReserveNum) {
        this.totalReserveNum = totalReserveNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public double getTotalUntaxReceiveAmount() {
        return totalUntaxReceiveAmount;
    }

    public void setTotalUntaxReceiveAmount(double totalUntaxReceiveAmount) {
        this.totalUntaxReceiveAmount = totalUntaxReceiveAmount;
    }

    public double getUnRefundTotalOutAmount() {
        return unRefundTotalOutAmount;
    }

    public void setUnRefundTotalOutAmount(double unRefundTotalOutAmount) {
        this.unRefundTotalOutAmount = unRefundTotalOutAmount;
    }

    public double getUnRefundTotalProfitAmount() {
        return unRefundTotalProfitAmount;
    }

    public void setUnRefundTotalProfitAmount(double unRefundTotalProfitAmount) {
        this.unRefundTotalProfitAmount = unRefundTotalProfitAmount;
    }

    public String getUnRefundTotalProfitRate() {
        return unRefundTotalProfitRate;
    }

    public void setUnRefundTotalProfitRate(String unRefundTotalProfitRate) {
        this.unRefundTotalProfitRate = unRefundTotalProfitRate;
    }
}
