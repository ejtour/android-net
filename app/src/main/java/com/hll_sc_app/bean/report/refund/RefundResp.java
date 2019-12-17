package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class RefundResp {

    /**
     * 总账期金额
     */
    private double totalAccountAmount;
    /**
     * 总银行金额
     */
    private double totalBankCardAmount;
    /**
     * 总订单数
     */
    private int totalBillNum;
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
     * 总退货单数
     */
    private int totalRefundBillNum;
    /**
     * 总退货集团数
     */
    private int totalRefundGroupNum;
    /**
     * 总退货商品数
     */
    private int totalRefundProductNum;
    /**
     * 总占比
     */
    private String totalRefundProportion;
    /**
     * 总退货门店数
     */
    private int totalRefundShopNum;
    /**
     * 总条数
     */
    private int totalSize;

    private List<RefundBean> groupVoList;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add(CommonUtils.formatNumber(totalRefundBillNum)); // 退单数
        list.add(CommonUtils.formatNumber(totalRefundGroupNum) + "/" + CommonUtils.formatNumber(totalRefundShopNum)); // 退货客户数
        list.add(CommonUtils.formatNumber(totalRefundProductNum)); // 退货商品数
        list.add(CommonUtils.formatMoney(totalCashAmount)); // 现金
        list.add(CommonUtils.formatMoney(totalBankCardAmount)); // 银行卡
        list.add(CommonUtils.formatMoney(totalOnLineAmount)); // 线上支付
        list.add(CommonUtils.formatMoney(totalAccountAmount)); // 账期支付
        list.add(CommonUtils.formatMoney(totalRefundAmount)); // 小计
        list.add(CommonUtils.formatNumber(totalBillNum)); // 订单数
        list.add(totalRefundProportion); // 退货占比
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

    public int getTotalBillNum() {
        return totalBillNum;
    }

    public void setTotalBillNum(int totalBillNum) {
        this.totalBillNum = totalBillNum;
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

    public int getTotalRefundGroupNum() {
        return totalRefundGroupNum;
    }

    public void setTotalRefundGroupNum(int totalRefundGroupNum) {
        this.totalRefundGroupNum = totalRefundGroupNum;
    }

    public int getTotalRefundProductNum() {
        return totalRefundProductNum;
    }

    public void setTotalRefundProductNum(int totalRefundProductNum) {
        this.totalRefundProductNum = totalRefundProductNum;
    }

    public String getTotalRefundProportion() {
        return totalRefundProportion;
    }

    public void setTotalRefundProportion(String totalRefundProportion) {
        this.totalRefundProportion = totalRefundProportion;
    }

    public int getTotalRefundShopNum() {
        return totalRefundShopNum;
    }

    public void setTotalRefundShopNum(int totalRefundShopNum) {
        this.totalRefundShopNum = totalRefundShopNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<RefundBean> getGroupVoList() {
        return groupVoList;
    }

    public void setGroupVoList(List<RefundBean> groupVoList) {
        this.groupVoList = groupVoList;
    }
}
