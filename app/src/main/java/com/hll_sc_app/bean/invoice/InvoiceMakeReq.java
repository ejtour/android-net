package com.hll_sc_app.bean.invoice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public class InvoiceMakeReq implements Parcelable {
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
    private List<String> shopIDList;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 退款订单ID列表
     */
    private List<String> refundBillIDList;
    /**
     * 纳税人识别号
     */
    private String taxpayerNum;
    /**
     * 联系电话
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

    public List<String> getShopIDList() {
        return shopIDList;
    }

    public void setShopIDList(List<String> shopIDList) {
        this.shopIDList = shopIDList;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public List<String> getRefundBillIDList() {
        return refundBillIDList;
    }

    public void setRefundBillIDList(List<String> refundBillIDList) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.address);
        dest.writeStringList(this.billIDList);
        dest.writeString(this.groupID);
        dest.writeString(this.invoiceNO);
        dest.writeDouble(this.invoicePrice);
        dest.writeDouble(this.orderAmount);
        dest.writeDouble(this.refundAmount);
        dest.writeString(this.invoiceTitle);
        dest.writeInt(this.invoiceType);
        dest.writeString(this.note);
        dest.writeString(this.openBank);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeStringList(this.shopIDList);
        dest.writeString(this.receiver);
        dest.writeStringList(this.refundBillIDList);
        dest.writeString(this.taxpayerNum);
        dest.writeString(this.telephone);
        dest.writeInt(this.titleType);
        dest.writeString(this.userID);
    }

    public InvoiceMakeReq() {
    }

    protected InvoiceMakeReq(Parcel in) {
        this.account = in.readString();
        this.address = in.readString();
        this.billIDList = in.createStringArrayList();
        this.groupID = in.readString();
        this.invoiceNO = in.readString();
        this.invoicePrice = in.readDouble();
        this.orderAmount = in.readDouble();
        this.refundAmount = in.readDouble();
        this.invoiceTitle = in.readString();
        this.invoiceType = in.readInt();
        this.note = in.readString();
        this.openBank = in.readString();
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.shopIDList = in.createStringArrayList();
        this.receiver = in.readString();
        this.refundBillIDList = in.createStringArrayList();
        this.taxpayerNum = in.readString();
        this.telephone = in.readString();
        this.titleType = in.readInt();
        this.userID = in.readString();
    }

    public static final Parcelable.Creator<InvoiceMakeReq> CREATOR = new Parcelable.Creator<InvoiceMakeReq>() {
        @Override
        public InvoiceMakeReq createFromParcel(Parcel source) {
            return new InvoiceMakeReq(source);
        }

        @Override
        public InvoiceMakeReq[] newArray(int size) {
            return new InvoiceMakeReq[size];
        }
    };
}
