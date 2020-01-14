package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class RefundDetailsBean implements IStringArrayGenerator {

    /**
     * 账期金额
     */
    private double accountAmount;
    /**
     * 银行金额
     */
    private double bankCardAmount;
    /**
     * 订单数
     */
    private int billNum;
    /**
     * 现金金额
     */
    private double cashAmount;
    /**
     * 在线金额
     */
    private double onLineAmount;
    /**
     * 退货日期
     */
    private String refundBillDate;
    /**
     * 退货单数
     */
    private int refundBillNum;
    /**
     * 退货集团数
     */
    private int refundGroupNum;
    /**
     * 退货商品数
     */
    private double refundProductNum;
    /**
     * 退货占比
     */
    private String refundProportion;
    /**
     * 退货门店数
     */
    private int refundShopNum;
    /**
     * 小计金额
     */
    private double subRefundAmount;
    /**
     * 订货金额
     */
    private double totalBillAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(refundBillDate, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(CommonUtils.formatNumber(refundBillNum)); // 退单数
        list.add(CommonUtils.formatNumber(refundGroupNum) + "/" + CommonUtils.formatNumber(refundShopNum)); // 退货客户数
        list.add(CommonUtils.formatNumber(refundProductNum)); //退货商品数
        list.add(CommonUtils.formatMoney(cashAmount)); // 现金
        list.add(CommonUtils.formatMoney(bankCardAmount)); // 银行卡
        list.add(CommonUtils.formatMoney(onLineAmount)); // 线上
        list.add(CommonUtils.formatMoney(accountAmount)); // 账期
        list.add(CommonUtils.formatMoney(subRefundAmount)); // 小计
        list.add(CommonUtils.formatNumber(billNum)); // 订单数
        list.add(refundProportion); // 退货占比
        return list;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public double getBankCardAmount() {
        return bankCardAmount;
    }

    public void setBankCardAmount(double bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public double getOnLineAmount() {
        return onLineAmount;
    }

    public void setOnLineAmount(double onLineAmount) {
        this.onLineAmount = onLineAmount;
    }

    public String getRefundBillDate() {
        return refundBillDate;
    }

    public void setRefundBillDate(String refundBillDate) {
        this.refundBillDate = refundBillDate;
    }

    public int getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(int refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public int getRefundGroupNum() {
        return refundGroupNum;
    }

    public void setRefundGroupNum(int refundGroupNum) {
        this.refundGroupNum = refundGroupNum;
    }

    public double getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(double refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public String getRefundProportion() {
        return refundProportion;
    }

    public void setRefundProportion(String refundProportion) {
        this.refundProportion = refundProportion;
    }

    public int getRefundShopNum() {
        return refundShopNum;
    }

    public void setRefundShopNum(int refundShopNum) {
        this.refundShopNum = refundShopNum;
    }

    public double getSubRefundAmount() {
        return subRefundAmount;
    }

    public void setSubRefundAmount(double subRefundAmount) {
        this.subRefundAmount = subRefundAmount;
    }

    public double getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(double totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }
}
