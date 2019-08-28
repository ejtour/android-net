package com.hll_sc_app.bean.report.refund;

import java.util.List;

public class RefundedCustomerResp {

    /**
     * 总账期金额
     */
    private String totalAccountAmount;
    /**
     * 总银行卡金额
     */
    private String totalBankCardAmount;
    /**
     * 总现金金额
     */
    private String    totalCashAmount;
    /**
     * 总在线金额
     */
    private String    totalOnLineAmount;
    /**
     * 总金额
     */
    private String    totalRefundAmount;
    /**
     * 采购商集团ID
     */
    private String totalRefundBillNum;
    /**
     * 总退款单数
     */
    private String totalRefundProductNum;
    /**
     * 总条数
     */
    private int    totalSize;

    private List<RefundedCustomerItem> groupVoList;

    public String getTotalAccountAmount() {
        return totalAccountAmount;
    }

    public void setTotalAccountAmount(String totalAccountAmount) {
        this.totalAccountAmount = totalAccountAmount;
    }

    public String getTotalBankCardAmount() {
        return totalBankCardAmount;
    }

    public void setTotalBankCardAmount(String totalBankCardAmount) {
        this.totalBankCardAmount = totalBankCardAmount;
    }

    public String getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(String totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    public String getTotalOnLineAmount() {
        return totalOnLineAmount;
    }

    public void setTotalOnLineAmount(String totalOnLineAmount) {
        this.totalOnLineAmount = totalOnLineAmount;
    }

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getTotalRefundBillNum() {
        return totalRefundBillNum;
    }

    public void setTotalRefundBillNum(String totalRefundBillNum) {
        this.totalRefundBillNum = totalRefundBillNum;
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

    public List<RefundedCustomerItem> getGroupVoList() {
        return groupVoList;
    }

    public void setGroupVoList(List<RefundedCustomerItem> groupVoList) {
        this.groupVoList = groupVoList;
    }
}
