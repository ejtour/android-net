package com.hll_sc_app.bean.report.credit;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CreditDetailsResp {
    private List<CreditDetailsBean> records;
    private double totalPayAmount;
    private String totalPayAmountRate;
    private double totalReceiveAmount;
    private int totalSize;
    private double totalUnPayAmount;
    private double totalUntaxReceiveAmount;

    public List<CreditDetailsBean> getRecords() {
        return records;
    }

    public void setRecords(List<CreditDetailsBean> records) {
        this.records = records;
    }

    public double getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(double totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public String getTotalPayAmountRate() {
        return totalPayAmountRate;
    }

    public void setTotalPayAmountRate(String totalPayAmountRate) {
        this.totalPayAmountRate = totalPayAmountRate;
    }

    public double getTotalReceiveAmount() {
        return totalReceiveAmount;
    }

    public void setTotalReceiveAmount(double totalReceiveAmount) {
        this.totalReceiveAmount = totalReceiveAmount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public double getTotalUnPayAmount() {
        return totalUnPayAmount;
    }

    public void setTotalUnPayAmount(double totalUnPayAmount) {
        this.totalUnPayAmount = totalUnPayAmount;
    }

    public double getTotalUntaxReceiveAmount() {
        return totalUntaxReceiveAmount;
    }

    public void setTotalUntaxReceiveAmount(double totalUntaxReceiveAmount) {
        this.totalUntaxReceiveAmount = totalUntaxReceiveAmount;
    }
}
