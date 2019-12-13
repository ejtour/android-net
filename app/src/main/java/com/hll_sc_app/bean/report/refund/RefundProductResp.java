package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 退货商品响应列表
 */
public class RefundProductResp {

    /**
     * 退货金额合计
     */
    private double totalRefundAmount;
    /**
     * 商品数量合计
     */
    private int totalRefundProductNum;
    /**
     * 总条数
     */
    private int totalSize;
    private List<RefundProductBean> records;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计"); // 采购商集团
        list.add("- - - -"); // 商品名称
        list.add("- - - -"); // 商品规格
        list.add("- -"); // 单位
        list.add(CommonUtils.formatNumber(totalRefundProductNum)); // 数量
        list.add(CommonUtils.formatMoney(totalRefundAmount)); // 退款金额
        return list;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public int getTotalRefundProductNum() {
        return totalRefundProductNum;
    }

    public void setTotalRefundProductNum(int totalRefundProductNum) {
        this.totalRefundProductNum = totalRefundProductNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<RefundProductBean> getRecords() {
        return records;
    }

    public void setRecords(List<RefundProductBean> records) {
        this.records = records;
    }
}
