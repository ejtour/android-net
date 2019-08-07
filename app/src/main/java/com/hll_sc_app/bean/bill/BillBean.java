package com.hll_sc_app.bean.bill;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */

public class BillBean {
    private String actionTime;
    private String shopName;
    private int billNum;
    private String billCreateTime;
    private String supplyShopName;
    private String createby;
    private String groupLogoUrl;
    private String purchaserID;
    private int isConfirm;
    private String paymentDay;
    private int settlementStatus;
    private int action;
    private String id;
    private String startPaymentDay;
    private String endPaymentDay;
    private String billStatementNo;
    private String actionBy;
    private String supplyShopID;
    private String groupID;
    private String purchaserName;
    private String accountDayFlag;
    private double totalAmount;
    private String groupName;
    private String createTime;
    private String shopID;
    private double totalIncomeAmount;
    private String pdfUrl;
    private double totalRefundAmount;
    private String paymentSettleDay;
    private List<BillDetailsBean> records;

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public String getBillCreateTime() {
        return billCreateTime;
    }

    public void setBillCreateTime(String billCreateTime) {
        this.billCreateTime = billCreateTime;
    }

    public String getSupplyShopName() {
        return supplyShopName;
    }

    public void setSupplyShopName(String supplyShopName) {
        this.supplyShopName = supplyShopName;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getGroupLogoUrl() {
        return groupLogoUrl;
    }

    public void setGroupLogoUrl(String groupLogoUrl) {
        this.groupLogoUrl = groupLogoUrl;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public int getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(String paymentDay) {
        this.paymentDay = paymentDay;
    }

    public int getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(int settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartPaymentDay() {
        return startPaymentDay;
    }

    public void setStartPaymentDay(String startPaymentDay) {
        this.startPaymentDay = startPaymentDay;
    }

    public String getEndPaymentDay() {
        return endPaymentDay;
    }

    public void setEndPaymentDay(String endPaymentDay) {
        this.endPaymentDay = endPaymentDay;
    }

    public String getBillStatementNo() {
        return billStatementNo;
    }

    public void setBillStatementNo(String billStatementNo) {
        this.billStatementNo = billStatementNo;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getAccountDayFlag() {
        return accountDayFlag;
    }

    public void setAccountDayFlag(String accountDayFlag) {
        this.accountDayFlag = accountDayFlag;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public double getTotalIncomeAmount() {
        return totalIncomeAmount;
    }

    public void setTotalIncomeAmount(double totalIncomeAmount) {
        this.totalIncomeAmount = totalIncomeAmount;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getPaymentSettleDay() {
        return paymentSettleDay;
    }

    public void setPaymentSettleDay(String paymentSettleDay) {
        this.paymentSettleDay = paymentSettleDay;
    }

    public List<BillDetailsBean> getRecords() {
        return records;
    }

    public void setRecords(List<BillDetailsBean> records) {
        this.records = records;
    }
}
