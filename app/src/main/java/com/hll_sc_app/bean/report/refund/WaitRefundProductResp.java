package com.hll_sc_app.bean.report.refund;

import java.util.List;

public class WaitRefundProductResp {

    /**
     * 总金额
     */
    private String totalRefundAmount;
    /**
     * 总数量
     */
    private String   totalRefundNum;
    /**
     * 总条数
     */
    private int    totalSize;
    private List<WaitRefundProductItem> records;

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getTotalRefundNum() {
        return totalRefundNum;
    }

    public void setTotalRefundNum(String totalRefundNum) {
        this.totalRefundNum = totalRefundNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<WaitRefundProductItem> getRecords() {
        return records;
    }

    public void setRecords(List<WaitRefundProductItem> records) {
        this.records = records;
    }
}
