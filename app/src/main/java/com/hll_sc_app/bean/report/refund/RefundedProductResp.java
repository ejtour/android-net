package com.hll_sc_app.bean.report.refund;

import java.util.List;

/**
 * 退货商品响应列表
 */
public class RefundedProductResp {

    /**
     * 退货金额合计
     */
    private String totalRefundAmount;
    /**
     * 商品数量合计
     */
    private String totalRefundProductNum;
    /**
     * 总条数
     */
    private int    totalSize;
    private List<RefundedProductItem> records;

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getTotalRefundProductNum() {
        return totalRefundProductNum;
    }

    public void setTotalRefundProductNum(String totalRefundProductNum) {
        this.totalRefundProductNum = totalRefundProductNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<RefundedProductItem> getRecords() {
        return records;
    }

    public void setRecords(List<RefundedProductItem> records) {
        this.records = records;
    }
}
