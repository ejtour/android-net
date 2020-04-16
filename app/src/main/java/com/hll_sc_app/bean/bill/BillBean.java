package com.hll_sc_app.bean.bill;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */

public class BillBean implements Parcelable {
    private String actionTime;
    private String shopName;
    private int billNum;
    private int billStatementFlag;
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
    private boolean isSelected;
    private List<BillDetailsBean> records;
    private int payee;
    private String objection;

    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    public int getPayee() {
        return payee;
    }

    public void setPayee(int payee) {
        this.payee = payee;
    }


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

    public int getBillStatementFlag() {
        return billStatementFlag;
    }

    public void setBillStatementFlag(int billStatementFlag) {
        this.billStatementFlag = billStatementFlag;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BillBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.shopName);
        dest.writeInt(this.billNum);
        dest.writeInt(this.billStatementFlag);
        dest.writeString(this.billCreateTime);
        dest.writeString(this.supplyShopName);
        dest.writeString(this.createby);
        dest.writeString(this.groupLogoUrl);
        dest.writeString(this.purchaserID);
        dest.writeInt(this.isConfirm);
        dest.writeString(this.paymentDay);
        dest.writeInt(this.settlementStatus);
        dest.writeInt(this.action);
        dest.writeString(this.id);
        dest.writeString(this.startPaymentDay);
        dest.writeString(this.endPaymentDay);
        dest.writeString(this.billStatementNo);
        dest.writeString(this.actionBy);
        dest.writeString(this.supplyShopID);
        dest.writeString(this.groupID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.accountDayFlag);
        dest.writeDouble(this.totalAmount);
        dest.writeString(this.groupName);
        dest.writeString(this.createTime);
        dest.writeString(this.shopID);
        dest.writeDouble(this.totalIncomeAmount);
        dest.writeString(this.pdfUrl);
        dest.writeDouble(this.totalRefundAmount);
        dest.writeString(this.paymentSettleDay);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeInt(this.payee);
        dest.writeString(this.objection);
    }

    protected BillBean(Parcel in) {
        this.actionTime = in.readString();
        this.shopName = in.readString();
        this.billNum = in.readInt();
        this.billStatementFlag = in.readInt();
        this.billCreateTime = in.readString();
        this.supplyShopName = in.readString();
        this.createby = in.readString();
        this.groupLogoUrl = in.readString();
        this.purchaserID = in.readString();
        this.isConfirm = in.readInt();
        this.paymentDay = in.readString();
        this.settlementStatus = in.readInt();
        this.action = in.readInt();
        this.id = in.readString();
        this.startPaymentDay = in.readString();
        this.endPaymentDay = in.readString();
        this.billStatementNo = in.readString();
        this.actionBy = in.readString();
        this.supplyShopID = in.readString();
        this.groupID = in.readString();
        this.purchaserName = in.readString();
        this.accountDayFlag = in.readString();
        this.totalAmount = in.readDouble();
        this.groupName = in.readString();
        this.createTime = in.readString();
        this.shopID = in.readString();
        this.totalIncomeAmount = in.readDouble();
        this.pdfUrl = in.readString();
        this.totalRefundAmount = in.readDouble();
        this.paymentSettleDay = in.readString();
        this.isSelected = in.readByte() != 0;
        this.payee = in.readInt();
        this.objection = in.readString();
    }

    public static final Creator<BillBean> CREATOR = new Creator<BillBean>() {
        @Override
        public BillBean createFromParcel(Parcel source) {
            return new BillBean(source);
        }

        @Override
        public BillBean[] newArray(int size) {
            return new BillBean[size];
        }
    };
}
