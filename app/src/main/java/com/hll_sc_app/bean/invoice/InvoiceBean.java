package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class InvoiceBean {
    private String createTime;
    private String id;
    private String imagePath;
    private String invoiceNO;
    private double invoicePrice;
    private int invoiceStatus;
    private String invoiceTime;
    private int invoiceType;
    private String purchaserID;
    private String purchaserName;
    private String purchaserShopID;
    private String purchaserShopName;
    private String rejectTime;

    private String note;
    private int titleType;
    private String actionTime;
    private String openBank;
    private double orderSettlementAmount;
    private String userID;
    private double refundSettlementAmount;
    private String createby;
    private double orderAmount;
    private int action;
    private String taxpayerNum;
    private String invoiceTitle;
    private double refundAmount;
    private int settlemented;
    private String address;
    private String receiver;
    private String actionBy;
    private String groupID;
    private String telephone;
    private int billTotal;
    private String odmId;
    private String account;

    private int shopTotal;

    private String invoiceVoucher;
    private List<ReturnRecordBean> returnRecordList;

    private String rejectReason;

    private String businessBeginDate;
    private String businessEndDate;

    private List<String> shopIDList;

    public String getInvoiceTypeLabel() {
        switch (invoiceType) {
            case 1:
                return "纸质普通发票";
            case 2:
                return "纸质专用发票";
            case 3:
                return "电子普通发票";
            default:
                return "";
        }
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
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

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public String getPurchaserShopName() {
        return purchaserShopName;
    }

    public void setPurchaserShopName(String purchaserShopName) {
        this.purchaserShopName = purchaserShopName;
    }

    public String getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(String rejectTime) {
        this.rejectTime = rejectTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public double getOrderSettlementAmount() {
        return orderSettlementAmount;
    }

    public void setOrderSettlementAmount(double orderSettlementAmount) {
        this.orderSettlementAmount = orderSettlementAmount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getRefundSettlementAmount() {
        return refundSettlementAmount;
    }

    public void setRefundSettlementAmount(double refundSettlementAmount) {
        this.refundSettlementAmount = refundSettlementAmount;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getTaxpayerNum() {
        return taxpayerNum;
    }

    public void setTaxpayerNum(String taxpayerNum) {
        this.taxpayerNum = taxpayerNum;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getSettlemented() {
        return settlemented;
    }

    public void setSettlemented(int settlemented) {
        this.settlemented = settlemented;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(int billTotal) {
        this.billTotal = billTotal;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInvoiceVoucher() {
        return invoiceVoucher;
    }

    public void setInvoiceVoucher(String invoiceVoucher) {
        this.invoiceVoucher = invoiceVoucher;
    }

    public List<ReturnRecordBean> getReturnRecordList() {
        return returnRecordList;
    }

    public void setReturnRecordList(List<ReturnRecordBean> returnRecordList) {
        this.returnRecordList = returnRecordList;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(int shopTotal) {
        this.shopTotal = shopTotal;
    }

    public String getBusinessBeginDate() {
        return businessBeginDate;
    }

    public void setBusinessBeginDate(String businessBeginDate) {
        this.businessBeginDate = businessBeginDate;
    }

    public String getBusinessEndDate() {
        return businessEndDate;
    }

    public void setBusinessEndDate(String businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public List<String> getShopIDList() {
        return shopIDList;
    }

    public void setShopIDList(List<String> shopIDList) {
        this.shopIDList = shopIDList;
    }
}
