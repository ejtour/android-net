package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class RefundCustomerResp {

    /**
     * 总账期金额
     */
    private double totalAccountAmount;
    /**
     * 总银行卡金额
     */
    private double totalBankCardAmount;
    /**
     * 总现金金额
     */
    private double totalCashAmount;
    /**
     * 总在线金额
     */
    private double totalOnLineAmount;
    /**
     * 总金额
     */
    private double totalRefundAmount;
    /**
     * 采购商集团ID
     */
    private int totalRefundBillNum;
    /**
     * 总退款单数
     */
    private double totalRefundProductNum;
    /**
     * 总条数
     */
    private int totalSize;

    private List<RefundCustomerBean> groupVoList;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计"); // 采购商集团
        list.add("- - - -"); // 采购商门店
        list.add(CommonUtils.formatNumber(totalRefundBillNum)); // 退单数
        list.add(CommonUtils.formatNumber(totalRefundProductNum)); // 退货商品数
        list.add(CommonUtils.formatMoney(totalCashAmount)); // 现金
        list.add(CommonUtils.formatMoney(totalBankCardAmount)); // 银行卡
        list.add(CommonUtils.formatMoney(totalOnLineAmount)); // 线上支付
        list.add(CommonUtils.formatMoney(totalAccountAmount)); // 账期支付
        list.add(CommonUtils.formatMoney(totalRefundAmount)); // 小计
        return list;
    }

    public double getTotalAccountAmount() {
        return totalAccountAmount;
    }

    public void setTotalAccountAmount(double totalAccountAmount) {
        this.totalAccountAmount = totalAccountAmount;
    }

    public double getTotalBankCardAmount() {
        return totalBankCardAmount;
    }

    public void setTotalBankCardAmount(double totalBankCardAmount) {
        this.totalBankCardAmount = totalBankCardAmount;
    }

    public double getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(double totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    public double getTotalOnLineAmount() {
        return totalOnLineAmount;
    }

    public void setTotalOnLineAmount(double totalOnLineAmount) {
        this.totalOnLineAmount = totalOnLineAmount;
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

    public double getTotalRefundProductNum() {
        return totalRefundProductNum;
    }

    public void setTotalRefundProductNum(double totalRefundProductNum) {
        this.totalRefundProductNum = totalRefundProductNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<RefundCustomerBean> getGroupVoList() {
        return groupVoList;
    }

    public void setGroupVoList(List<RefundCustomerBean> groupVoList) {
        this.groupVoList = groupVoList;
    }
}
