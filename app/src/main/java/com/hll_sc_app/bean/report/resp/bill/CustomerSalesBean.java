package com.hll_sc_app.bean.report.resp.bill;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class CustomerSalesBean implements IStringArrayGenerator {

    private double averageAmount;
    private String cooperationShopNum;
    private int orderNum;
    private String purchaserID;
    private String purchaserName;
    private double refundAmount;
    private int refundBillNum;
    private String shopName;
    private double subtotalAmount;
    private double tradeAmount;
    private int validOrderNum;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(purchaserName); // 采购商名称
        if (cooperationShopNum != null) { // 门店销售汇总
            list.add(CommonUtils.formatNumber(cooperationShopNum)); // 合作门店数
        } else {
            list.add(shopName); // 门店名称
        }
        list.add(CommonUtils.formatNumber(orderNum)); // 订单数
        list.add(CommonUtils.formatNumber(validOrderNum)); // 有效订单数
        list.add(CommonUtils.formatMoney(tradeAmount)); // 交易金额
        list.add(CommonUtils.formatMoney(averageAmount)); // 单均
        list.add(CommonUtils.formatNumber(refundBillNum)); // 退单数
        list.add(CommonUtils.formatMoney(refundAmount)); // 退货金额
        list.add(CommonUtils.formatMoney(subtotalAmount)); // 小计金额
        return list;
    }

    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    public String getCooperationShopNum() {
        return cooperationShopNum;
    }

    public void setCooperationShopNum(String cooperationShopNum) {
        this.cooperationShopNum = cooperationShopNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(int refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public int getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(int validOrderNum) {
        this.validOrderNum = validOrderNum;
    }
}
