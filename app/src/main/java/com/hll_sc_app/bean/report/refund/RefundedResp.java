package com.hll_sc_app.bean.report.refund;

import java.util.List;

public class RefundedResp {

    /**
     * 总账期金额
     */
    private String  totalAccountAmount;
    /**
     * 总银行金额
     */
    private String  totalBankCardAmount;
    /**
     * 总订单数
     */
    private long    totalBillNum;
    /**
     * 总现金金额
     */
    private String  totalCashAmount;
    /**
     * 总在线金额
     */
    private String  totalOnLineAmount;
    /**
     * 总金额
     */
    private String  totalRefundAmount;
    /**
     * 总退货单数
     */
    private long    totalRefundBillNum;
    /**
     * 总退货集团数
     */
    private long    totalRefundGroupNum	;
    /**
     * 总退货商品数
     */
    private long    totalRefundProductNum;
    /**
     * 总占比
     */
    private String  totalRefundProportion;
    /**
     * 总退货门店数
     */
    private long    totalRefundShopNum;
    /**
     * 总条数
     */
    private int     totalSize;

    private List<RefundedItem> groupVoList;


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

    public long getTotalBillNum() {
        return totalBillNum;
    }

    public void setTotalBillNum(long totalBillNum) {
        this.totalBillNum = totalBillNum;
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

    public long getTotalRefundBillNum() {
        return totalRefundBillNum;
    }

    public void setTotalRefundBillNum(long totalRefundBillNum) {
        this.totalRefundBillNum = totalRefundBillNum;
    }

    public long getTotalRefundGroupNum() {
        return totalRefundGroupNum;
    }

    public void setTotalRefundGroupNum(long totalRefundGroupNum) {
        this.totalRefundGroupNum = totalRefundGroupNum;
    }

    public long getTotalRefundProductNum() {
        return totalRefundProductNum;
    }

    public void setTotalRefundProductNum(long totalRefundProductNum) {
        this.totalRefundProductNum = totalRefundProductNum;
    }

    public String getTotalRefundProportion() {
        return totalRefundProportion;
    }

    public void setTotalRefundProportion(String totalRefundProportion) {
        this.totalRefundProportion = totalRefundProportion;
    }

    public long getTotalRefundShopNum() {
        return totalRefundShopNum;
    }

    public void setTotalRefundShopNum(long totalRefundShopNum) {
        this.totalRefundShopNum = totalRefundShopNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<RefundedItem> getGroupVoList() {
        return groupVoList;
    }

    public void setGroupVoList(List<RefundedItem> groupVoList) {
        this.groupVoList = groupVoList;
    }
}
