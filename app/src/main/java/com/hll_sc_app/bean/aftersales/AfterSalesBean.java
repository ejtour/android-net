package com.hll_sc_app.bean.aftersales;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AfterSalesBean implements Parcelable {
    private int cancelRole;
    private long returnGoodsAuditTime;
    private String onlyReceiveOrder;
    private int refundBillDate;
    private String canceler;
    private int clientSource;
    private int payType;
    private int refundBillType;
    private long refuseTime;
    private int action;
    private String id;
    private String refundBillCreateBy;
    private int shipperID;
    private String payOrderNo;
    private long signTime;
    private String supplyShopID;
    private String subBillID;
    private String groupID;
    private String shipperName;
    private int paymentWay;
    private String purchaserName;
    private String refundAuditBy;
    private long refundAuditTime;
    private double totalAmount;
    private String groupName;
    private int refundBillStatus;
    private String shopID;
    private String refundBillRemark;
    private SaleInfoVoBean saleInfoVo;
    private String signBy;
    private long actionTime;
    private long deliveryTime;
    private int billSource;
    private String shopName;
    private String refundVoucher;
    private String returnGoodsAuditBy;
    private int refundType;
    private String supplyShopName;
    private String createby;
    private String purchaserID;
    private String deliveryBy;
    private String erpGroupID;
    private String erpShopID;
    private String refuseBy;
    private double subBillInspectionAmount;
    private double priceDifferences;
    private String cancelReason;
    private String erpRefundBillNo;
    private long refundBillCreateTime;
    private String erpRefundBillID;
    private String spareField4;
    private String spareField3;
    private String actionBy;
    private int refundReason;
    private int billStatementID;
    private String refundBillNo;
    private int isGenBillSatement;
    private int refundPayStatus;
    private String subBillNo;
    private int operateModel;
    private String refundExplain;
    private long createTime;
    private double totalNum;
    private String refuseReason;
    private String spareField2;
    private int homologous;
    private String spareField1;
    private List<Integer> buttonList;
    private List<AfterSalesDetailsBean> detailList;
    private String refundReasonDesc;
    private boolean isSelected;
    private String receiverMobile;

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getRefundReasonDesc() {
        return refundReasonDesc;
    }

    public void setRefundReasonDesc(String refundReasonDesc) {
        this.refundReasonDesc = refundReasonDesc;
    }

    public boolean canModify() {
        return billSource != 1 && refundBillStatus == 1 &&
                !TextUtils.isEmpty(onlyReceiveOrder) && Integer.valueOf(onlyReceiveOrder) != 2;
    }

    public double getPriceDifferences() {
        return priceDifferences;
    }

    public void setPriceDifferences(double priceDifferences) {
        this.priceDifferences = priceDifferences;
    }

    public int getCancelRole() {
        return cancelRole;
    }

    public void setCancelRole(int cancelRole) {
        this.cancelRole = cancelRole;
    }

    public long getReturnGoodsAuditTime() {
        return returnGoodsAuditTime;
    }

    public void setReturnGoodsAuditTime(long returnGoodsAuditTime) {
        this.returnGoodsAuditTime = returnGoodsAuditTime;
    }

    public String getOnlyReceiveOrder() {
        return onlyReceiveOrder;
    }

    public void setOnlyReceiveOrder(String onlyReceiveOrder) {
        this.onlyReceiveOrder = onlyReceiveOrder;
    }

    public int getRefundBillDate() {
        return refundBillDate;
    }

    public void setRefundBillDate(int refundBillDate) {
        this.refundBillDate = refundBillDate;
    }

    public String getCanceler() {
        return canceler;
    }

    public void setCanceler(String canceler) {
        this.canceler = canceler;
    }

    public int getClientSource() {
        return clientSource;
    }

    public void setClientSource(int clientSource) {
        this.clientSource = clientSource;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getRefundBillType() {
        return refundBillType;
    }

    public void setRefundBillType(int refundBillType) {
        this.refundBillType = refundBillType;
    }

    public long getRefuseTime() {
        return refuseTime;
    }

    public void setRefuseTime(long refuseTime) {
        this.refuseTime = refuseTime;
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

    public String getRefundBillCreateBy() {
        return refundBillCreateBy;
    }

    public void setRefundBillCreateBy(String refundBillCreateBy) {
        this.refundBillCreateBy = refundBillCreateBy;
    }

    public int getShipperID() {
        return shipperID;
    }

    public void setShipperID(int shipperID) {
        this.shipperID = shipperID;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public int getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(int paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getRefundAuditBy() {
        return refundAuditBy;
    }

    public void setRefundAuditBy(String refundAuditBy) {
        this.refundAuditBy = refundAuditBy;
    }

    public long getRefundAuditTime() {
        return refundAuditTime;
    }

    public void setRefundAuditTime(long refundAuditTime) {
        this.refundAuditTime = refundAuditTime;
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

    public int getRefundBillStatus() {
        return refundBillStatus;
    }

    public void setRefundBillStatus(int refundBillStatus) {
        this.refundBillStatus = refundBillStatus;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getRefundBillRemark() {
        return refundBillRemark;
    }

    public void setRefundBillRemark(String refundBillRemark) {
        this.refundBillRemark = refundBillRemark;
    }

    public SaleInfoVoBean getSaleInfoVo() {
        return saleInfoVo;
    }

    public void setSaleInfoVo(SaleInfoVoBean saleInfoVo) {
        this.saleInfoVo = saleInfoVo;
    }

    public String getSignBy() {
        return signBy;
    }

    public void setSignBy(String signBy) {
        this.signBy = signBy;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getBillSource() {
        return billSource;
    }

    public void setBillSource(int billSource) {
        this.billSource = billSource;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRefundVoucher() {
        return refundVoucher;
    }

    public void setRefundVoucher(String refundVoucher) {
        this.refundVoucher = refundVoucher;
    }

    public String getReturnGoodsAuditBy() {
        return returnGoodsAuditBy;
    }

    public void setReturnGoodsAuditBy(String returnGoodsAuditBy) {
        this.returnGoodsAuditBy = returnGoodsAuditBy;
    }

    public int getRefundType() {
        return refundType;
    }

    public void setRefundType(int refundType) {
        this.refundType = refundType;
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

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getErpGroupID() {
        return erpGroupID;
    }

    public void setErpGroupID(String erpGroupID) {
        this.erpGroupID = erpGroupID;
    }

    public String getErpShopID() {
        return erpShopID;
    }

    public void setErpShopID(String erpShopID) {
        this.erpShopID = erpShopID;
    }

    public String getRefuseBy() {
        return refuseBy;
    }

    public void setRefuseBy(String refuseBy) {
        this.refuseBy = refuseBy;
    }

    public double getSubBillInspectionAmount() {
        return subBillInspectionAmount;
    }

    public void setSubBillInspectionAmount(double subBillInspectionAmount) {
        this.subBillInspectionAmount = subBillInspectionAmount;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getErpRefundBillNo() {
        return erpRefundBillNo;
    }

    public void setErpRefundBillNo(String erpRefundBillNo) {
        this.erpRefundBillNo = erpRefundBillNo;
    }

    public long getRefundBillCreateTime() {
        return refundBillCreateTime;
    }

    public void setRefundBillCreateTime(long refundBillCreateTime) {
        this.refundBillCreateTime = refundBillCreateTime;
    }

    public String getErpRefundBillID() {
        return erpRefundBillID;
    }

    public void setErpRefundBillID(String erpRefundBillID) {
        this.erpRefundBillID = erpRefundBillID;
    }

    public String getSpareField4() {
        return spareField4;
    }

    public void setSpareField4(String spareField4) {
        this.spareField4 = spareField4;
    }

    public String getSpareField3() {
        return spareField3;
    }

    public void setSpareField3(String spareField3) {
        this.spareField3 = spareField3;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public int getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(int refundReason) {
        this.refundReason = refundReason;
    }

    public int getBillStatementID() {
        return billStatementID;
    }

    public void setBillStatementID(int billStatementID) {
        this.billStatementID = billStatementID;
    }

    public String getRefundBillNo() {
        return refundBillNo;
    }

    public void setRefundBillNo(String refundBillNo) {
        this.refundBillNo = refundBillNo;
    }

    public int getIsGenBillSatement() {
        return isGenBillSatement;
    }

    public void setIsGenBillSatement(int isGenBillSatement) {
        this.isGenBillSatement = isGenBillSatement;
    }

    public int getRefundPayStatus() {
        return refundPayStatus;
    }

    public void setRefundPayStatus(int refundPayStatus) {
        this.refundPayStatus = refundPayStatus;
    }

    public String getSubBillNo() {
        return subBillNo;
    }

    public void setSubBillNo(String subBillNo) {
        this.subBillNo = subBillNo;
    }

    public int getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(int operateModel) {
        this.operateModel = operateModel;
    }

    public String getRefundExplain() {
        return refundExplain;
    }

    public void setRefundExplain(String refundExplain) {
        this.refundExplain = refundExplain;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getSpareField2() {
        return spareField2;
    }

    public void setSpareField2(String spareField2) {
        this.spareField2 = spareField2;
    }

    public int getHomologous() {
        return homologous;
    }

    public void setHomologous(int homologous) {
        this.homologous = homologous;
    }

    public String getSpareField1() {
        return spareField1;
    }

    public void setSpareField1(String spareField1) {
        this.spareField1 = spareField1;
    }

    public List<Integer> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<Integer> buttonList) {
        this.buttonList = buttonList;
    }

    public List<AfterSalesDetailsBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<AfterSalesDetailsBean> detailList) {
        this.detailList = detailList;
    }

    public static class SaleInfoVoBean implements Parcelable {

        private String salesmanName;
        private int salesmanID;
        private String salesmanPhone;

        public String getSalesmanName() {
            return salesmanName;
        }

        public void setSalesmanName(String salesmanName) {
            this.salesmanName = salesmanName;
        }

        public int getSalesmanID() {
            return salesmanID;
        }

        public void setSalesmanID(int salesmanID) {
            this.salesmanID = salesmanID;
        }

        public String getSalesmanPhone() {
            return salesmanPhone;
        }

        public void setSalesmanPhone(String salesmanPhone) {
            this.salesmanPhone = salesmanPhone;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.salesmanName);
            dest.writeInt(this.salesmanID);
            dest.writeString(this.salesmanPhone);
        }

        public SaleInfoVoBean() {
        }

        protected SaleInfoVoBean(Parcel in) {
            this.salesmanName = in.readString();
            this.salesmanID = in.readInt();
            this.salesmanPhone = in.readString();
        }

        public static final Creator<SaleInfoVoBean> CREATOR = new Creator<SaleInfoVoBean>() {
            @Override
            public SaleInfoVoBean createFromParcel(Parcel source) {
                return new SaleInfoVoBean(source);
            }

            @Override
            public SaleInfoVoBean[] newArray(int size) {
                return new SaleInfoVoBean[size];
            }
        };
    }

    public AfterSalesBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cancelRole);
        dest.writeLong(this.returnGoodsAuditTime);
        dest.writeString(this.onlyReceiveOrder);
        dest.writeInt(this.refundBillDate);
        dest.writeString(this.canceler);
        dest.writeInt(this.clientSource);
        dest.writeInt(this.payType);
        dest.writeInt(this.refundBillType);
        dest.writeLong(this.refuseTime);
        dest.writeInt(this.action);
        dest.writeString(this.id);
        dest.writeString(this.refundBillCreateBy);
        dest.writeInt(this.shipperID);
        dest.writeString(this.payOrderNo);
        dest.writeLong(this.signTime);
        dest.writeString(this.supplyShopID);
        dest.writeString(this.subBillID);
        dest.writeString(this.groupID);
        dest.writeString(this.shipperName);
        dest.writeInt(this.paymentWay);
        dest.writeString(this.purchaserName);
        dest.writeString(this.refundAuditBy);
        dest.writeLong(this.refundAuditTime);
        dest.writeDouble(this.totalAmount);
        dest.writeString(this.groupName);
        dest.writeInt(this.refundBillStatus);
        dest.writeString(this.shopID);
        dest.writeString(this.refundBillRemark);
        dest.writeParcelable(this.saleInfoVo, flags);
        dest.writeString(this.signBy);
        dest.writeLong(this.actionTime);
        dest.writeLong(this.deliveryTime);
        dest.writeInt(this.billSource);
        dest.writeString(this.shopName);
        dest.writeString(this.refundVoucher);
        dest.writeString(this.returnGoodsAuditBy);
        dest.writeInt(this.refundType);
        dest.writeString(this.supplyShopName);
        dest.writeString(this.createby);
        dest.writeString(this.purchaserID);
        dest.writeString(this.deliveryBy);
        dest.writeString(this.erpGroupID);
        dest.writeString(this.erpShopID);
        dest.writeString(this.refuseBy);
        dest.writeDouble(this.subBillInspectionAmount);
        dest.writeDouble(this.priceDifferences);
        dest.writeString(this.cancelReason);
        dest.writeString(this.erpRefundBillNo);
        dest.writeLong(this.refundBillCreateTime);
        dest.writeString(this.erpRefundBillID);
        dest.writeString(this.spareField4);
        dest.writeString(this.spareField3);
        dest.writeString(this.actionBy);
        dest.writeInt(this.refundReason);
        dest.writeInt(this.billStatementID);
        dest.writeString(this.refundBillNo);
        dest.writeInt(this.isGenBillSatement);
        dest.writeInt(this.refundPayStatus);
        dest.writeString(this.subBillNo);
        dest.writeInt(this.operateModel);
        dest.writeString(this.refundExplain);
        dest.writeLong(this.createTime);
        dest.writeDouble(this.totalNum);
        dest.writeString(this.refuseReason);
        dest.writeString(this.spareField2);
        dest.writeInt(this.homologous);
        dest.writeString(this.spareField1);
        dest.writeList(this.buttonList);
        dest.writeTypedList(this.detailList);
        dest.writeString(this.refundReasonDesc);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.receiverMobile);
    }

    protected AfterSalesBean(Parcel in) {
        this.cancelRole = in.readInt();
        this.returnGoodsAuditTime = in.readLong();
        this.onlyReceiveOrder = in.readString();
        this.refundBillDate = in.readInt();
        this.canceler = in.readString();
        this.clientSource = in.readInt();
        this.payType = in.readInt();
        this.refundBillType = in.readInt();
        this.refuseTime = in.readLong();
        this.action = in.readInt();
        this.id = in.readString();
        this.refundBillCreateBy = in.readString();
        this.shipperID = in.readInt();
        this.payOrderNo = in.readString();
        this.signTime = in.readLong();
        this.supplyShopID = in.readString();
        this.subBillID = in.readString();
        this.groupID = in.readString();
        this.shipperName = in.readString();
        this.paymentWay = in.readInt();
        this.purchaserName = in.readString();
        this.refundAuditBy = in.readString();
        this.refundAuditTime = in.readLong();
        this.totalAmount = in.readDouble();
        this.groupName = in.readString();
        this.refundBillStatus = in.readInt();
        this.shopID = in.readString();
        this.refundBillRemark = in.readString();
        this.saleInfoVo = in.readParcelable(SaleInfoVoBean.class.getClassLoader());
        this.signBy = in.readString();
        this.actionTime = in.readLong();
        this.deliveryTime = in.readLong();
        this.billSource = in.readInt();
        this.shopName = in.readString();
        this.refundVoucher = in.readString();
        this.returnGoodsAuditBy = in.readString();
        this.refundType = in.readInt();
        this.supplyShopName = in.readString();
        this.createby = in.readString();
        this.purchaserID = in.readString();
        this.deliveryBy = in.readString();
        this.erpGroupID = in.readString();
        this.erpShopID = in.readString();
        this.refuseBy = in.readString();
        this.subBillInspectionAmount = in.readDouble();
        this.priceDifferences = in.readDouble();
        this.cancelReason = in.readString();
        this.erpRefundBillNo = in.readString();
        this.refundBillCreateTime = in.readLong();
        this.erpRefundBillID = in.readString();
        this.spareField4 = in.readString();
        this.spareField3 = in.readString();
        this.actionBy = in.readString();
        this.refundReason = in.readInt();
        this.billStatementID = in.readInt();
        this.refundBillNo = in.readString();
        this.isGenBillSatement = in.readInt();
        this.refundPayStatus = in.readInt();
        this.subBillNo = in.readString();
        this.operateModel = in.readInt();
        this.refundExplain = in.readString();
        this.createTime = in.readLong();
        this.totalNum = in.readDouble();
        this.refuseReason = in.readString();
        this.spareField2 = in.readString();
        this.homologous = in.readInt();
        this.spareField1 = in.readString();
        this.buttonList = new ArrayList<Integer>();
        in.readList(this.buttonList, Integer.class.getClassLoader());
        this.detailList = in.createTypedArrayList(AfterSalesDetailsBean.CREATOR);
        this.refundReasonDesc = in.readString();
        this.isSelected = in.readByte() != 0;
        this.receiverMobile = in.readString();
    }

    public static final Creator<AfterSalesBean> CREATOR = new Creator<AfterSalesBean>() {
        @Override
        public AfterSalesBean createFromParcel(Parcel source) {
            return new AfterSalesBean(source);
        }

        @Override
        public AfterSalesBean[] newArray(int size) {
            return new AfterSalesBean[size];
        }
    };
}
