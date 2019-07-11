package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 报价单 Bean
 *
 * @author zhuyingsong
 * @date 2019-07-08
 */
public class QuotationBean implements Parcelable {
    /**
     * 未审核
     */
    public static final int BILL_STATUS_NO_AUDIT = 1;
    /**
     * 已审核
     */
    public static final int BILL_STATUS_AUDIT = 2;
    /**
     * 已驳回
     */
    public static final int BILL_STATUS_REJECT = 3;
    /**
     * 已取消
     */
    public static final int BILL_STATUS_CANCEL = 4;
    /**
     * 已到期
     */
    public static final int BILL_STATUS_EXPIRE = 5;
    /**
     * 放弃
     */
    public static final int BILL_STATUS_ABANDON = 6;
    public static final Creator<QuotationBean> CREATOR = new Creator<QuotationBean>() {
        @Override
        public QuotationBean createFromParcel(Parcel source) {
            return new QuotationBean(source);
        }

        @Override
        public QuotationBean[] newArray(int size) {
            return new QuotationBean[size];
        }
    };
    private String priceEndDate;
    private String reason;
    private String actionTime;
    private String auditBy;
    private String isSendEmail;
    private String shopName;
    private String priceStartDate;
    private String billCreateTime;
    private String createby;
    private String purchaserID;
    private String isWarehouse;
    private int billStatus;
    private String action;
    private String billRemark;
    private String id;
    private String billNo;
    private String billCreateBy;
    private String actionBy;
    private String billType;
    private String groupID;
    private String billDate;
    private String productNum;
    private String templateID;
    private String purchaserName;
    private String auditTime;
    private String createTime;
    private String templateName;
    private String shopIDs;
    private String shopIDNum;
    /**
     * 是否选择了全部店铺（选择了全部店铺传1，未选择全部传0）
     */
    private String isAllShop;
    private boolean select;

    public QuotationBean() {
    }

    protected QuotationBean(Parcel in) {
        this.priceEndDate = in.readString();
        this.reason = in.readString();
        this.actionTime = in.readString();
        this.auditBy = in.readString();
        this.isSendEmail = in.readString();
        this.shopName = in.readString();
        this.priceStartDate = in.readString();
        this.billCreateTime = in.readString();
        this.createby = in.readString();
        this.purchaserID = in.readString();
        this.isWarehouse = in.readString();
        this.billStatus = in.readInt();
        this.action = in.readString();
        this.billRemark = in.readString();
        this.id = in.readString();
        this.billNo = in.readString();
        this.billCreateBy = in.readString();
        this.actionBy = in.readString();
        this.billType = in.readString();
        this.groupID = in.readString();
        this.billDate = in.readString();
        this.productNum = in.readString();
        this.templateID = in.readString();
        this.purchaserName = in.readString();
        this.auditTime = in.readString();
        this.createTime = in.readString();
        this.templateName = in.readString();
        this.shopIDs = in.readString();
        this.shopIDNum = in.readString();
        this.isAllShop = in.readString();
        this.select = in.readByte() != 0;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getIsAllShop() {
        return isAllShop;
    }

    public void setIsAllShop(String isAllShop) {
        this.isAllShop = isAllShop;
    }

    public String getPriceEndDate() {
        return priceEndDate;
    }

    public void setPriceEndDate(String priceEndDate) {
        this.priceEndDate = priceEndDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(String isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPriceStartDate() {
        return priceStartDate;
    }

    public void setPriceStartDate(String priceStartDate) {
        this.priceStartDate = priceStartDate;
    }

    public String getBillCreateTime() {
        return billCreateTime;
    }

    public void setBillCreateTime(String billCreateTime) {
        this.billCreateTime = billCreateTime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getIsWarehouse() {
        return isWarehouse;
    }

    public void setIsWarehouse(String isWarehouse) {
        this.isWarehouse = isWarehouse;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillCreateBy() {
        return billCreateBy;
    }

    public void setBillCreateBy(String billCreateBy) {
        this.billCreateBy = billCreateBy;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getShopIDs() {
        return shopIDs;
    }

    public void setShopIDs(String shopIDs) {
        this.shopIDs = shopIDs;
    }

    public String getShopIDNum() {
        return shopIDNum;
    }

    public void setShopIDNum(String shopIDNum) {
        this.shopIDNum = shopIDNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.priceEndDate);
        dest.writeString(this.reason);
        dest.writeString(this.actionTime);
        dest.writeString(this.auditBy);
        dest.writeString(this.isSendEmail);
        dest.writeString(this.shopName);
        dest.writeString(this.priceStartDate);
        dest.writeString(this.billCreateTime);
        dest.writeString(this.createby);
        dest.writeString(this.purchaserID);
        dest.writeString(this.isWarehouse);
        dest.writeInt(this.billStatus);
        dest.writeString(this.action);
        dest.writeString(this.billRemark);
        dest.writeString(this.id);
        dest.writeString(this.billNo);
        dest.writeString(this.billCreateBy);
        dest.writeString(this.actionBy);
        dest.writeString(this.billType);
        dest.writeString(this.groupID);
        dest.writeString(this.billDate);
        dest.writeString(this.productNum);
        dest.writeString(this.templateID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.auditTime);
        dest.writeString(this.createTime);
        dest.writeString(this.templateName);
        dest.writeString(this.shopIDs);
        dest.writeString(this.shopIDNum);
        dest.writeString(this.isAllShop);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }
}
