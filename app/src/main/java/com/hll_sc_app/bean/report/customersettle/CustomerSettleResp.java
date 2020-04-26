package com.hll_sc_app.bean.report.customersettle;

import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

public class CustomerSettleResp {
    private double allNoPaymentAmount;
    private double allPaymentAmount;
    private double allTotalPrice;
    private double beforeAllNoPaymentAmount;
    private double noInvoiceTotalPrice;
    private double sumInvoiceAmount;
    private double totalAllNoPaymentAmount;

    public List<CustomerSettleBean> convertToBeanList() {
        List<CustomerSettleBean> list = new ArrayList<>();
        List<NameValue> nestedList = new ArrayList<>();
        nestedList.add(new NameValue("本期进货金额", String.format("¥%s", CommonUtils.formatMoney(allTotalPrice))));
        nestedList.add(new NameValue("已付款", String.format("¥%s", CommonUtils.formatMoney(allPaymentAmount))));
        nestedList.add(new NameValue("未付款", String.format("¥%s", CommonUtils.formatMoney(allNoPaymentAmount))));
        list.add(new CustomerSettleBean("本期结算信息", nestedList));
        nestedList = new ArrayList<>();
        nestedList.add(new NameValue("本期前未结算金额", String.format("¥%s", CommonUtils.formatMoney(beforeAllNoPaymentAmount))));
        nestedList.add(new NameValue("合计未结算金额", String.format("¥%s", CommonUtils.formatMoney(totalAllNoPaymentAmount))));
        list.add(new CustomerSettleBean("未结算信息", nestedList));
        nestedList = new ArrayList<>();
        nestedList.add(new NameValue("已开发票金额", String.format("¥%s", CommonUtils.formatMoney(sumInvoiceAmount))));
        nestedList.add(new NameValue("未开发票金额", String.format("¥%s", CommonUtils.formatMoney(noInvoiceTotalPrice))));
        list.add(new CustomerSettleBean("发票信息", nestedList));
        return list;
    }

    public double getAllNoPaymentAmount() {
        return allNoPaymentAmount;
    }

    public void setAllNoPaymentAmount(double allNoPaymentAmount) {
        this.allNoPaymentAmount = allNoPaymentAmount;
    }

    public double getAllPaymentAmount() {
        return allPaymentAmount;
    }

    public void setAllPaymentAmount(double allPaymentAmount) {
        this.allPaymentAmount = allPaymentAmount;
    }

    public double getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(double allTotalPrice) {
        this.allTotalPrice = allTotalPrice;
    }

    public double getBeforeAllNoPaymentAmount() {
        return beforeAllNoPaymentAmount;
    }

    public void setBeforeAllNoPaymentAmount(double beforeAllNoPaymentAmount) {
        this.beforeAllNoPaymentAmount = beforeAllNoPaymentAmount;
    }

    public double getNoInvoiceTotalPrice() {
        return noInvoiceTotalPrice;
    }

    public void setNoInvoiceTotalPrice(double noInvoiceTotalPrice) {
        this.noInvoiceTotalPrice = noInvoiceTotalPrice;
    }

    public double getSumInvoiceAmount() {
        return sumInvoiceAmount;
    }

    public void setSumInvoiceAmount(double sumInvoiceAmount) {
        this.sumInvoiceAmount = sumInvoiceAmount;
    }

    public double getTotalAllNoPaymentAmount() {
        return totalAllNoPaymentAmount;
    }

    public void setTotalAllNoPaymentAmount(double totalAllNoPaymentAmount) {
        this.totalAllNoPaymentAmount = totalAllNoPaymentAmount;
    }
}
