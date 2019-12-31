package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class RefundCustomerBean implements IStringArrayGenerator {

    /**
     * 账期金额
     */
    private double accountAmount;
    /**
     * 银行卡金额
     */
    private double bankCardAmount;
    /**
     * 现金金额
     */
    private double cashAmount;
    /**
     * 在线支付金额
     */
    private double onLineAmount;
    /**
     * 采购商集团名称
     */
    private String purchaserName;
    /**
     * 采购商集团ID
     */
    private String purchaserID;
    /**
     * 退货退款单数
     */
    private int refundBillNum;
    /**
     * 已退商品数
     */
    private double refundProductNum;
    /**
     * 门店ID
     */
    private String shopID;
    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 小计金额
     */
    private double subRefundAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(purchaserName);// 采购商集团
        list.add(shopName); // 采购商门店
        list.add(CommonUtils.formatNumber(refundBillNum)); // 退单数
        list.add(CommonUtils.formatNumber(refundProductNum)); //退货商品数
        list.add(CommonUtils.formatMoney(cashAmount)); // 现金
        list.add(CommonUtils.formatMoney(bankCardAmount)); //银行卡
        list.add(CommonUtils.formatMoney(onLineAmount)); //线上
        list.add(CommonUtils.formatMoney(accountAmount)); //账期
        list.add(CommonUtils.formatMoney(subRefundAmount)); //小计
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

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public int getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(int refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public double getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(double refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getSubRefundAmount() {
        return subRefundAmount;
    }

    public void setSubRefundAmount(double subRefundAmount) {
        this.subRefundAmount = subRefundAmount;
    }
}
