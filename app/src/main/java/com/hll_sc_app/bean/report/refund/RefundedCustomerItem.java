package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class RefundedCustomerItem implements IStringArrayGenerator {

    /**
     * 账期金额
     */
    private String accountAmount;
    /**
     * 银行卡金额
     */
    private String bankCardAmount;
    /**
     * 现金金额
     */
    private String    cashAmount;
    /**
     * 在线支付金额
     */
    private String    onLineAmount;
    /**
     * 采购商集团名称
     */
    private String    purchaserName;
    /**
     * 采购商集团ID
     */
    private String purchaserID;
    /**
     * 退货退款单数
     */
    private String refundBillNum;
    /**
     * 已退商品数
     */
    private long    refundProductNum;
    /**
     * 门店ID
     */
    private String    shopID;
    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 小计金额
     */
    private String subRefundAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(getPurchaserName());// 采购商集团
        list.add(getShopName()); // 采购商门店
        list.add(getRefundBillNum()); // 退单数
        list.add(getRefundProductNum() + ""); //退货商品数
        list.add(CommonUtils.formatMoney(Double.parseDouble(getCashAmount()))); // 现金
        list.add(CommonUtils.formatMoney(Double.parseDouble(getBankCardAmount()))); //银行卡
        list.add(CommonUtils.formatMoney(Double.parseDouble(getOnLineAmount()))); //线上
        list.add(CommonUtils.formatMoney(Double.parseDouble(getAccountAmount()))); //账期
        list.add(CommonUtils.formatMoney(Double.parseDouble(getSubRefundAmount()))); //小计
        return list;
    }

    public String getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(String accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getBankCardAmount() {
        return bankCardAmount;
    }

    public void setBankCardAmount(String bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getOnLineAmount() {
        return onLineAmount;
    }

    public void setOnLineAmount(String onLineAmount) {
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

    public String getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(String refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public long getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(long refundProductNum) {
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

    public String getSubRefundAmount() {
        return subRefundAmount;
    }

    public void setSubRefundAmount(String subRefundAmount) {
        this.subRefundAmount = subRefundAmount;
    }
}
