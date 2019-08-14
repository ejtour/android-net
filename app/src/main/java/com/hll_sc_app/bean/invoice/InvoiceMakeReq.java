package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public class InvoiceMakeReq {
    /**
     * 账号
     */
    private String account;
    /**
     * 地址
     */
    private String address;
    /**
     * 要关联的订单ID列表
     */
    private List<String> billIDList;
    /**
     * 集团ID
     */
    private String groupID;
    /**
     * 门店logo地址
     */
    private String imagePath;
    /**
     * 发票号
     */
    private String invoiceNO;
    /**
     * 发票金额
     */
    private double invoicePrice;
    /**
     * 申请开票订单金额
     */
    private double orderAmount;
    /**
     * 申请开票退款金额
     */
    private double refundAmount;
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    /**
     * 发票类型(1-普通发票，2-专用发票)
     */
    private int invoiceType;
    /**
     * 备注
     */
    private String note;
    /**
     * 开户行
     */
    private String openBank;
    /**
     * 采购商ID
     */
    private String purchaserID;
    /**
     * 采购商名称
     */
    private String purchaserName;
    /**
     * 采购商门店ID
     */
    private String purchaserShopID;
    /**
     * 采购商店铺名称
     */
    private String purchaserShopName;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 退款订单ID列表
     */
    private List<Integer> refundBillIDList;
    /**
     * 纳税人识别号
     */
    private String taxpayerNum;
    /**
     * 	联系电话
     */
    private String telephone;
    /**
     * 发票抬头类型(1-企业,2-个人/事业单位)
     */
    private int titleType;
    /**
     * 登录用户ID
     */
    private String userID;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getBillIDList() {
        return billIDList;
    }

    public void setBillIDList(List<String> billIDList) {
        this.billIDList = billIDList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
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

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public List<Integer> getRefundBillIDList() {
        return refundBillIDList;
    }

    public void setRefundBillIDList(List<Integer> refundBillIDList) {
        this.refundBillIDList = refundBillIDList;
    }

    public String getTaxpayerNum() {
        return taxpayerNum;
    }

    public void setTaxpayerNum(String taxpayerNum) {
        this.taxpayerNum = taxpayerNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
