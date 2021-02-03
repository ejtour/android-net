package com.hll_sc_app.bean.order;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderResp implements Parcelable {
    public static final Creator<OrderResp> CREATOR = new Creator<OrderResp>() {
        @Override
        public OrderResp createFromParcel(Parcel source) {
            return new OrderResp(source);
        }

        @Override
        public OrderResp[] newArray(int size) {
            return new OrderResp[size];
        }
    };
    private String couponDiscountRuleName;
    private String couponDiscountName;
    private String userID;
    private int canceler;
    private String subBillCreateTime;
    private int payee;
    private double couponAmount;
    private int payType;
    private int rejectReason;
    private double adjustmentCouponSubAmount;
    private int action;
    private String subBillCreateBy;
    private String subBillExecuteDate;
    private String salesManID;
    private int subBillType;
    private String shipperID;
    private String payOrderNo;
    private int masterBillID;
    private double adjustmentDiscountSubAmount;
    private String supplyBillNo;
    private String acceptTime;
    private String shipperName;
    private int paymentWay;
    private String couponID;
    private int nextDayDelivery;
    private String groupName;
    private String checkTime;
    private double inspectionDepositTotalAmount;
    private int signMinDiff;
    private int isExchange;
    private String ordererMobile;
    private String actionTime;
    private int flag;
    private String deliveryTime;
    private int billSource;
    private int paymentSettleDay;
    private double orderDiscountAmount;
    private String supplyShopName;
    private double amountPaid;
    private double orderTotalAmount;
    private int wareHourseID;
    private String deliveryBy;
    private String purchaseBillNo;
    private String cancelReason;
    private int productNo;
    private double refundAmount;
    private String actionBy;
    private int billStatementID;
    private String masterBillNo;
    private String receiverMobile;
    private String agencyID;
    private int isGenBillSatement;
    private int originSubBillID;
    private String orderDiscountName;
    private String acceptBy;
    private String expressName;
    private int isCheck;
    private String createTime;
    private int isSupplement;
    private double orderDiscountSubAmount;
    private double refundedDepositTotalAmount;
    private double couponSubAmount;
    private int checkMinDiff;
    private int orderDiscountRuleType;
    private int deliverType;
    private String rejectExplain;
    private int shipperType;
    private double ruleDiscountValue;
    private String originalBillNo;
    private String signTime;
    private String supplyShopID;
    private String subBillID;
    private String groupID;
    private int exchangeBillID;
    private String purchaserName;
    private String subBillRemark;
    private int serialNo;
    private String receiverAddress;
    private String houseAddress;
    private double totalAmount;
    private String extGroupID;
    private double inspectionTotalAmount;
    private double orderDepositTotalAmount;
    private int orderDiscountRuleID;
    private double adjustmentDepositTotalAmount;
    private String shopID;
    private String signBy;
    private String linkPhone;
    private String expressNo;
    private double inspectionCouponSubAmount;
    private String salesManName;
    private String shopName;
    private String orderDiscountRuleName;
    private int refundBillID;
    private String createby;
    private int total;
    private String purchaserID;
    private int settlementStatus;
    private int subBillStatus;
    private double inspectionDiscountSubAmount;
    private String billUpdateTime;
    private String orderDiscountID;
    private int isPay;
    private String receiverName;
    private String refundBillNo;
    private String subBillExecuteEndDate;
    private int subBillDate;
    private String subBillNo;
    private String agencyName;
    private String imgUrl;
    private double adjustmentTotalAmount;
    private String curRefundStatus;
    private String rejectVoucher;
    private String settlementTime;
    private String wareHourseName;
    private int subbillCategory;
    private int invoiceStatus;
    private boolean mIsSelected;
    private String driverId;
    private String driverName;
    private String mobilePhone;
    private String plateNumber;
    private List<Integer> buttonList;
    private int receiptRemaining;
    private String distributionStatus;
    private int productNum;
    private List<OrderDetailBean> billDetailList;
    private String stallID;
    private String stallName;
    private String latGaoDe;
    private String lonGaoDe;
    private String groupRemark;

    protected OrderResp(Parcel in) {
        this.couponDiscountRuleName = in.readString();
        this.couponDiscountName = in.readString();
        this.userID = in.readString();
        this.canceler = in.readInt();
        this.subBillCreateTime = in.readString();
        this.payee = in.readInt();
        this.couponAmount = in.readDouble();
        this.payType = in.readInt();
        this.rejectReason = in.readInt();
        this.adjustmentCouponSubAmount = in.readDouble();
        this.action = in.readInt();
        this.subBillCreateBy = in.readString();
        this.subBillExecuteDate = in.readString();
        this.salesManID = in.readString();
        this.subBillType = in.readInt();
        this.shipperID = in.readString();
        this.payOrderNo = in.readString();
        this.masterBillID = in.readInt();
        this.adjustmentDiscountSubAmount = in.readDouble();
        this.supplyBillNo = in.readString();
        this.acceptTime = in.readString();
        this.shipperName = in.readString();
        this.paymentWay = in.readInt();
        this.couponID = in.readString();
        this.nextDayDelivery = in.readInt();
        this.groupName = in.readString();
        this.checkTime = in.readString();
        this.inspectionDepositTotalAmount = in.readDouble();
        this.signMinDiff = in.readInt();
        this.isExchange = in.readInt();
        this.ordererMobile = in.readString();
        this.actionTime = in.readString();
        this.flag = in.readInt();
        this.deliveryTime = in.readString();
        this.billSource = in.readInt();
        this.paymentSettleDay = in.readInt();
        this.orderDiscountAmount = in.readDouble();
        this.supplyShopName = in.readString();
        this.amountPaid = in.readDouble();
        this.orderTotalAmount = in.readDouble();
        this.wareHourseID = in.readInt();
        this.deliveryBy = in.readString();
        this.purchaseBillNo = in.readString();
        this.cancelReason = in.readString();
        this.productNo = in.readInt();
        this.refundAmount = in.readDouble();
        this.actionBy = in.readString();
        this.billStatementID = in.readInt();
        this.masterBillNo = in.readString();
        this.receiverMobile = in.readString();
        this.agencyID = in.readString();
        this.isGenBillSatement = in.readInt();
        this.originSubBillID = in.readInt();
        this.orderDiscountName = in.readString();
        this.acceptBy = in.readString();
        this.expressName = in.readString();
        this.isCheck = in.readInt();
        this.createTime = in.readString();
        this.isSupplement = in.readInt();
        this.orderDiscountSubAmount = in.readDouble();
        this.refundedDepositTotalAmount = in.readDouble();
        this.couponSubAmount = in.readDouble();
        this.checkMinDiff = in.readInt();
        this.orderDiscountRuleType = in.readInt();
        this.deliverType = in.readInt();
        this.rejectExplain = in.readString();
        this.shipperType = in.readInt();
        this.ruleDiscountValue = in.readDouble();
        this.originalBillNo = in.readString();
        this.signTime = in.readString();
        this.supplyShopID = in.readString();
        this.subBillID = in.readString();
        this.groupID = in.readString();
        this.exchangeBillID = in.readInt();
        this.purchaserName = in.readString();
        this.subBillRemark = in.readString();
        this.serialNo = in.readInt();
        this.receiverAddress = in.readString();
        this.houseAddress = in.readString();
        this.totalAmount = in.readDouble();
        this.extGroupID = in.readString();
        this.inspectionTotalAmount = in.readDouble();
        this.orderDepositTotalAmount = in.readDouble();
        this.orderDiscountRuleID = in.readInt();
        this.adjustmentDepositTotalAmount = in.readDouble();
        this.shopID = in.readString();
        this.signBy = in.readString();
        this.linkPhone = in.readString();
        this.expressNo = in.readString();
        this.inspectionCouponSubAmount = in.readDouble();
        this.salesManName = in.readString();
        this.shopName = in.readString();
        this.orderDiscountRuleName = in.readString();
        this.refundBillID = in.readInt();
        this.createby = in.readString();
        this.total = in.readInt();
        this.purchaserID = in.readString();
        this.settlementStatus = in.readInt();
        this.subBillStatus = in.readInt();
        this.inspectionDiscountSubAmount = in.readDouble();
        this.billUpdateTime = in.readString();
        this.orderDiscountID = in.readString();
        this.isPay = in.readInt();
        this.receiverName = in.readString();
        this.refundBillNo = in.readString();
        this.subBillExecuteEndDate = in.readString();
        this.subBillDate = in.readInt();
        this.subBillNo = in.readString();
        this.agencyName = in.readString();
        this.imgUrl = in.readString();
        this.adjustmentTotalAmount = in.readDouble();
        this.curRefundStatus = in.readString();
        this.rejectVoucher = in.readString();
        this.settlementTime = in.readString();
        this.wareHourseName = in.readString();
        this.subbillCategory = in.readInt();
        this.invoiceStatus = in.readInt();
        this.mIsSelected = in.readByte() != 0;
        this.driverId = in.readString();
        this.driverName = in.readString();
        this.mobilePhone = in.readString();
        this.plateNumber = in.readString();
        this.buttonList = new ArrayList<Integer>();
        in.readList(this.buttonList, Integer.class.getClassLoader());
        this.receiptRemaining = in.readInt();
        this.distributionStatus = in.readString();
        this.productNum = in.readInt();
        this.billDetailList = in.createTypedArrayList(OrderDetailBean.CREATOR);
        this.stallID = in.readString();
        this.stallName = in.readString();
        this.latGaoDe = in.readString();
        this.lonGaoDe = in.readString();
        this.groupRemark = in.readString();
    }

    public OrderResp() {
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public String getLatGaoDe() {
        return latGaoDe;
    }

    public void setLatGaoDe(String latGaoDe) {
        this.latGaoDe = latGaoDe;
    }

    public String getLonGaoDe() {
        return lonGaoDe;
    }

    public void setLonGaoDe(String lonGaoDe) {
        this.lonGaoDe = lonGaoDe;
    }

    public String getStallID() {
        return stallID;
    }

    public void setStallID(String stallID) {
        this.stallID = stallID;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public boolean isCanSelect(String groupID) {
        if (TextUtils.isEmpty(groupID)) return false;
        return !(isCheck == 2 && groupID.equals(this.groupID)
                || isCheck == 1 && groupID.equals(this.agencyID));
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(String distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public double getDiffPrice() {
        return subBillStatus == 4 && payType == 3 ? getFee() : 0;
    }

    public double getFee() {
        return CommonUtils.addDouble(CommonUtils.subDouble(totalAmount, amountPaid), inspectionCouponSubAmount).doubleValue();
    }

    public String getTargetAddress() {
        return deliverType == 2 ? houseAddress : receiverAddress;
    }

    public String getTargetExecuteDate() {
        String HhMm = "2400";
        String formatDateStr = CalendarUtils.getDateFormatString(subBillExecuteDate, Constants.UNSIGNED_YYYY_MM_DD_HH_MM,
                Constants.SIGNED_YYYY_MM_DD_HH_MM);
        if (subBillExecuteEndDate.endsWith(HhMm)) return formatDateStr + "-" + "24:00";
        return formatDateStr + "-" + CalendarUtils.getDateFormatString(subBillExecuteEndDate,
                Constants.UNSIGNED_YYYY_MM_DD_HH_MM, Constants.SIGNED_HH_MM);
    }

    public String getCouponDiscountRuleName() {
        return couponDiscountRuleName;
    }

    public void setCouponDiscountRuleName(String couponDiscountRuleName) {
        this.couponDiscountRuleName = couponDiscountRuleName;
    }

    public String getCouponDiscountName() {
        return couponDiscountName;
    }

    public void setCouponDiscountName(String couponDiscountName) {
        this.couponDiscountName = couponDiscountName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCanceler() {
        return canceler;
    }

    public void setCanceler(int canceler) {
        this.canceler = canceler;
    }

    public String getSubBillCreateTime() {
        return subBillCreateTime;
    }

    public void setSubBillCreateTime(String subBillCreateTime) {
        this.subBillCreateTime = subBillCreateTime;
    }

    public int getPayee() {
        return payee;
    }

    public void setPayee(int payee) {
        this.payee = payee;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(int rejectReason) {
        this.rejectReason = rejectReason;
    }

    public double getAdjustmentCouponSubAmount() {
        return adjustmentCouponSubAmount;
    }

    public void setAdjustmentCouponSubAmount(double adjustmentCouponSubAmount) {
        this.adjustmentCouponSubAmount = adjustmentCouponSubAmount;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getSubBillCreateBy() {
        return subBillCreateBy;
    }

    public void setSubBillCreateBy(String subBillCreateBy) {
        this.subBillCreateBy = subBillCreateBy;
    }

    public String getSubBillExecuteDate() {
        return subBillExecuteDate;
    }

    public void setSubBillExecuteDate(String subBillExecuteDate) {
        this.subBillExecuteDate = subBillExecuteDate;
    }

    public String getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(String salesManID) {
        this.salesManID = salesManID;
    }

    public int getSubBillType() {
        return subBillType;
    }

    public void setSubBillType(int subBillType) {
        this.subBillType = subBillType;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public int getMasterBillID() {
        return masterBillID;
    }

    public void setMasterBillID(int masterBillID) {
        this.masterBillID = masterBillID;
    }

    public double getAdjustmentDiscountSubAmount() {
        return adjustmentDiscountSubAmount;
    }

    public void setAdjustmentDiscountSubAmount(double adjustmentDiscountSubAmount) {
        this.adjustmentDiscountSubAmount = adjustmentDiscountSubAmount;
    }

    public String getSupplyBillNo() {
        return supplyBillNo;
    }

    public void setSupplyBillNo(String supplyBillNo) {
        this.supplyBillNo = supplyBillNo;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
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

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public int getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(int nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public double getInspectionDepositTotalAmount() {
        return inspectionDepositTotalAmount;
    }

    public void setInspectionDepositTotalAmount(double inspectionDepositTotalAmount) {
        this.inspectionDepositTotalAmount = inspectionDepositTotalAmount;
    }

    public int getSignMinDiff() {
        return signMinDiff;
    }

    public void setSignMinDiff(int signMinDiff) {
        this.signMinDiff = signMinDiff;
    }

    public int getIsExchange() {
        return isExchange;
    }

    public void setIsExchange(int isExchange) {
        this.isExchange = isExchange;
    }

    public String getOrdererMobile() {
        return ordererMobile;
    }

    public void setOrdererMobile(String ordererMobile) {
        this.ordererMobile = ordererMobile;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getBillSource() {
        return billSource;
    }

    public void setBillSource(int billSource) {
        this.billSource = billSource;
    }

    public int getPaymentSettleDay() {
        return paymentSettleDay;
    }

    public void setPaymentSettleDay(int paymentSettleDay) {
        this.paymentSettleDay = paymentSettleDay;
    }

    public double getOrderDiscountAmount() {
        return orderDiscountAmount;
    }

    public void setOrderDiscountAmount(double orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public String getSupplyShopName() {
        return supplyShopName;
    }

    public void setSupplyShopName(String supplyShopName) {
        this.supplyShopName = supplyShopName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(double orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public int getWareHourseID() {
        return wareHourseID;
    }

    public void setWareHourseID(int wareHourseID) {
        this.wareHourseID = wareHourseID;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getPurchaseBillNo() {
        return purchaseBillNo;
    }

    public void setPurchaseBillNo(String purchaseBillNo) {
        this.purchaseBillNo = purchaseBillNo;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public int getBillStatementID() {
        return billStatementID;
    }

    public void setBillStatementID(int billStatementID) {
        this.billStatementID = billStatementID;
    }

    public String getMasterBillNo() {
        return masterBillNo;
    }

    public void setMasterBillNo(String masterBillNo) {
        this.masterBillNo = masterBillNo;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public int getIsGenBillSatement() {
        return isGenBillSatement;
    }

    public void setIsGenBillSatement(int isGenBillSatement) {
        this.isGenBillSatement = isGenBillSatement;
    }

    public int getOriginSubBillID() {
        return originSubBillID;
    }

    public void setOriginSubBillID(int originSubBillID) {
        this.originSubBillID = originSubBillID;
    }

    public String getOrderDiscountName() {
        return orderDiscountName;
    }

    public void setOrderDiscountName(String orderDiscountName) {
        this.orderDiscountName = orderDiscountName;
    }

    public String getAcceptBy() {
        return acceptBy;
    }

    public void setAcceptBy(String acceptBy) {
        this.acceptBy = acceptBy;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsSupplement() {
        return isSupplement;
    }

    public void setIsSupplement(int isSupplement) {
        this.isSupplement = isSupplement;
    }

    public double getOrderDiscountSubAmount() {
        return orderDiscountSubAmount;
    }

    public void setOrderDiscountSubAmount(double orderDiscountSubAmount) {
        this.orderDiscountSubAmount = orderDiscountSubAmount;
    }

    public double getRefundedDepositTotalAmount() {
        return refundedDepositTotalAmount;
    }

    public void setRefundedDepositTotalAmount(double refundedDepositTotalAmount) {
        this.refundedDepositTotalAmount = refundedDepositTotalAmount;
    }

    public double getCouponSubAmount() {
        return couponSubAmount;
    }

    public void setCouponSubAmount(double couponSubAmount) {
        this.couponSubAmount = couponSubAmount;
    }

    public int getCheckMinDiff() {
        return checkMinDiff;
    }

    public void setCheckMinDiff(int checkMinDiff) {
        this.checkMinDiff = checkMinDiff;
    }

    public int getOrderDiscountRuleType() {
        return orderDiscountRuleType;
    }

    public void setOrderDiscountRuleType(int orderDiscountRuleType) {
        this.orderDiscountRuleType = orderDiscountRuleType;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }

    public String getRejectExplain() {
        return rejectExplain;
    }

    public void setRejectExplain(String rejectExplain) {
        this.rejectExplain = rejectExplain;
    }

    public int getShipperType() {
        return shipperType;
    }

    public void setShipperType(int shipperType) {
        this.shipperType = shipperType;
    }

    public double getRuleDiscountValue() {
        return ruleDiscountValue;
    }

    public void setRuleDiscountValue(double ruleDiscountValue) {
        this.ruleDiscountValue = ruleDiscountValue;
    }

    public String getOriginalBillNo() {
        return originalBillNo;
    }

    public void setOriginalBillNo(String originalBillNo) {
        this.originalBillNo = originalBillNo;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
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

    public int getExchangeBillID() {
        return exchangeBillID;
    }

    public void setExchangeBillID(int exchangeBillID) {
        this.exchangeBillID = exchangeBillID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getSubBillRemark() {
        return subBillRemark;
    }

    public void setSubBillRemark(String subBillRemark) {
        this.subBillRemark = subBillRemark;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
    }

    public double getInspectionTotalAmount() {
        return inspectionTotalAmount;
    }

    public void setInspectionTotalAmount(double inspectionTotalAmount) {
        this.inspectionTotalAmount = inspectionTotalAmount;
    }

    public double getOrderDepositTotalAmount() {
        return orderDepositTotalAmount;
    }

    public void setOrderDepositTotalAmount(double orderDepositTotalAmount) {
        this.orderDepositTotalAmount = orderDepositTotalAmount;
    }

    public int getOrderDiscountRuleID() {
        return orderDiscountRuleID;
    }

    public void setOrderDiscountRuleID(int orderDiscountRuleID) {
        this.orderDiscountRuleID = orderDiscountRuleID;
    }

    public double getAdjustmentDepositTotalAmount() {
        return adjustmentDepositTotalAmount;
    }

    public void setAdjustmentDepositTotalAmount(double adjustmentDepositTotalAmount) {
        this.adjustmentDepositTotalAmount = adjustmentDepositTotalAmount;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getSignBy() {
        return signBy;
    }

    public void setSignBy(String signBy) {
        this.signBy = signBy;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public double getInspectionCouponSubAmount() {
        return inspectionCouponSubAmount;
    }

    public void setInspectionCouponSubAmount(double inspectionCouponSubAmount) {
        this.inspectionCouponSubAmount = inspectionCouponSubAmount;
    }

    public String getSalesManName() {
        return salesManName;
    }

    public void setSalesManName(String salesManName) {
        this.salesManName = salesManName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderDiscountRuleName() {
        return orderDiscountRuleName;
    }

    public void setOrderDiscountRuleName(String orderDiscountRuleName) {
        this.orderDiscountRuleName = orderDiscountRuleName;
    }

    public int getRefundBillID() {
        return refundBillID;
    }

    public void setRefundBillID(int refundBillID) {
        this.refundBillID = refundBillID;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public int getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(int settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public int getSubBillStatus() {
        return subBillStatus;
    }

    public void setSubBillStatus(int subBillStatus) {
        this.subBillStatus = subBillStatus;
    }

    public double getInspectionDiscountSubAmount() {
        return inspectionDiscountSubAmount;
    }

    public void setInspectionDiscountSubAmount(double inspectionDiscountSubAmount) {
        this.inspectionDiscountSubAmount = inspectionDiscountSubAmount;
    }

    public String getBillUpdateTime() {
        return billUpdateTime;
    }

    public void setBillUpdateTime(String billUpdateTime) {
        this.billUpdateTime = billUpdateTime;
    }

    public String getOrderDiscountID() {
        return orderDiscountID;
    }

    public void setOrderDiscountID(String orderDiscountID) {
        this.orderDiscountID = orderDiscountID;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRefundBillNo() {
        return refundBillNo;
    }

    public void setRefundBillNo(String refundBillNo) {
        this.refundBillNo = refundBillNo;
    }

    public String getSubBillExecuteEndDate() {
        return subBillExecuteEndDate;
    }

    public void setSubBillExecuteEndDate(String subBillExecuteEndDate) {
        this.subBillExecuteEndDate = subBillExecuteEndDate;
    }

    public int getSubBillDate() {
        return subBillDate;
    }

    public void setSubBillDate(int subBillDate) {
        this.subBillDate = subBillDate;
    }

    public String getSubBillNo() {
        return subBillNo;
    }

    public void setSubBillNo(String subBillNo) {
        this.subBillNo = subBillNo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getAdjustmentTotalAmount() {
        return adjustmentTotalAmount;
    }

    public void setAdjustmentTotalAmount(double adjustmentTotalAmount) {
        this.adjustmentTotalAmount = adjustmentTotalAmount;
    }

    public String getCurRefundStatus() {
        return curRefundStatus;
    }

    public void setCurRefundStatus(String curRefundStatus) {
        this.curRefundStatus = curRefundStatus;
    }

    public String getRejectVoucher() {
        return rejectVoucher;
    }

    public void setRejectVoucher(String rejectVoucher) {
        this.rejectVoucher = rejectVoucher;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getWareHourseName() {
        return wareHourseName;
    }

    public void setWareHourseName(String wareHourseName) {
        this.wareHourseName = wareHourseName;
    }

    public int getSubbillCategory() {
        return subbillCategory;
    }

    public void setSubbillCategory(int subbillCategory) {
        this.subbillCategory = subbillCategory;
    }

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    public List<Integer> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<Integer> buttonList) {
        this.buttonList = buttonList;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getReceiptRemaining() {
        return receiptRemaining;
    }

    public void setReceiptRemaining(int receiptRemaining) {
        this.receiptRemaining = receiptRemaining;
    }

    public List<OrderDetailBean> getBillDetailList() {
        return billDetailList;
    }

    public void setBillDetailList(List<OrderDetailBean> billDetailList) {
        this.billDetailList = billDetailList;
    }

    public String getTranPayType() {
        //1-货到付款 2- 账期支付 3-线上支付
        if (payType == 1) {
            return "货到付款";
        } else if (payType == 2) {
            return "账期支付";
        } else if (payType == 3) {
            return "线上支付";
        }
        return "";
    }

    public String getTranSettlementStatus(){
       //结算状态 1-未结算 2-已结算 3部分结算
        if (settlementStatus == 1) {
            return "未结算";
        } else if (settlementStatus == 2) {
            return "已结算";
        } else if (settlementStatus == 3) {
            return "部分结算";
        }
        return "";
    }

    public String getTranSubBillStatus(){
        //订单状态,1-待接单,2-待发货,3-已发货,4-待结算，5-已结算， 6-已完成，7-已取消
        if (subBillStatus == 1) {
            return "待接单";
        } else if (subBillStatus == 2) {
            return "待发货";
        } else if (subBillStatus == 3) {
            return "已发货";
        }else if (subBillStatus == 4) {
            return "待结算";
        } else if (subBillStatus == 5) {
            return "已结算";
        }else if (subBillStatus == 6) {
            return "已完成";
        } else if (subBillStatus == 7) {
            return "已取消";
        }
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.couponDiscountRuleName);
        dest.writeString(this.couponDiscountName);
        dest.writeString(this.userID);
        dest.writeInt(this.canceler);
        dest.writeString(this.subBillCreateTime);
        dest.writeInt(this.payee);
        dest.writeDouble(this.couponAmount);
        dest.writeInt(this.payType);
        dest.writeInt(this.rejectReason);
        dest.writeDouble(this.adjustmentCouponSubAmount);
        dest.writeInt(this.action);
        dest.writeString(this.subBillCreateBy);
        dest.writeString(this.subBillExecuteDate);
        dest.writeString(this.salesManID);
        dest.writeInt(this.subBillType);
        dest.writeString(this.shipperID);
        dest.writeString(this.payOrderNo);
        dest.writeInt(this.masterBillID);
        dest.writeDouble(this.adjustmentDiscountSubAmount);
        dest.writeString(this.supplyBillNo);
        dest.writeString(this.acceptTime);
        dest.writeString(this.shipperName);
        dest.writeInt(this.paymentWay);
        dest.writeString(this.couponID);
        dest.writeInt(this.nextDayDelivery);
        dest.writeString(this.groupName);
        dest.writeString(this.checkTime);
        dest.writeDouble(this.inspectionDepositTotalAmount);
        dest.writeInt(this.signMinDiff);
        dest.writeInt(this.isExchange);
        dest.writeString(this.ordererMobile);
        dest.writeString(this.actionTime);
        dest.writeInt(this.flag);
        dest.writeString(this.deliveryTime);
        dest.writeInt(this.billSource);
        dest.writeInt(this.paymentSettleDay);
        dest.writeDouble(this.orderDiscountAmount);
        dest.writeString(this.supplyShopName);
        dest.writeDouble(this.amountPaid);
        dest.writeDouble(this.orderTotalAmount);
        dest.writeInt(this.wareHourseID);
        dest.writeString(this.deliveryBy);
        dest.writeString(this.purchaseBillNo);
        dest.writeString(this.cancelReason);
        dest.writeInt(this.productNo);
        dest.writeDouble(this.refundAmount);
        dest.writeString(this.actionBy);
        dest.writeInt(this.billStatementID);
        dest.writeString(this.masterBillNo);
        dest.writeString(this.receiverMobile);
        dest.writeString(this.agencyID);
        dest.writeInt(this.isGenBillSatement);
        dest.writeInt(this.originSubBillID);
        dest.writeString(this.orderDiscountName);
        dest.writeString(this.acceptBy);
        dest.writeString(this.expressName);
        dest.writeInt(this.isCheck);
        dest.writeString(this.createTime);
        dest.writeInt(this.isSupplement);
        dest.writeDouble(this.orderDiscountSubAmount);
        dest.writeDouble(this.refundedDepositTotalAmount);
        dest.writeDouble(this.couponSubAmount);
        dest.writeInt(this.checkMinDiff);
        dest.writeInt(this.orderDiscountRuleType);
        dest.writeInt(this.deliverType);
        dest.writeString(this.rejectExplain);
        dest.writeInt(this.shipperType);
        dest.writeDouble(this.ruleDiscountValue);
        dest.writeString(this.originalBillNo);
        dest.writeString(this.signTime);
        dest.writeString(this.supplyShopID);
        dest.writeString(this.subBillID);
        dest.writeString(this.groupID);
        dest.writeInt(this.exchangeBillID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.subBillRemark);
        dest.writeInt(this.serialNo);
        dest.writeString(this.receiverAddress);
        dest.writeString(this.houseAddress);
        dest.writeDouble(this.totalAmount);
        dest.writeString(this.extGroupID);
        dest.writeDouble(this.inspectionTotalAmount);
        dest.writeDouble(this.orderDepositTotalAmount);
        dest.writeInt(this.orderDiscountRuleID);
        dest.writeDouble(this.adjustmentDepositTotalAmount);
        dest.writeString(this.shopID);
        dest.writeString(this.signBy);
        dest.writeString(this.linkPhone);
        dest.writeString(this.expressNo);
        dest.writeDouble(this.inspectionCouponSubAmount);
        dest.writeString(this.salesManName);
        dest.writeString(this.shopName);
        dest.writeString(this.orderDiscountRuleName);
        dest.writeInt(this.refundBillID);
        dest.writeString(this.createby);
        dest.writeInt(this.total);
        dest.writeString(this.purchaserID);
        dest.writeInt(this.settlementStatus);
        dest.writeInt(this.subBillStatus);
        dest.writeDouble(this.inspectionDiscountSubAmount);
        dest.writeString(this.billUpdateTime);
        dest.writeString(this.orderDiscountID);
        dest.writeInt(this.isPay);
        dest.writeString(this.receiverName);
        dest.writeString(this.refundBillNo);
        dest.writeString(this.subBillExecuteEndDate);
        dest.writeInt(this.subBillDate);
        dest.writeString(this.subBillNo);
        dest.writeString(this.agencyName);
        dest.writeString(this.imgUrl);
        dest.writeDouble(this.adjustmentTotalAmount);
        dest.writeString(this.curRefundStatus);
        dest.writeString(this.rejectVoucher);
        dest.writeString(this.settlementTime);
        dest.writeString(this.wareHourseName);
        dest.writeInt(this.subbillCategory);
        dest.writeInt(this.invoiceStatus);
        dest.writeByte(this.mIsSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.driverId);
        dest.writeString(this.driverName);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.plateNumber);
        dest.writeList(this.buttonList);
        dest.writeInt(this.receiptRemaining);
        dest.writeString(this.distributionStatus);
        dest.writeInt(this.productNum);
        dest.writeTypedList(this.billDetailList);
        dest.writeString(this.stallID);
        dest.writeString(this.stallName);
        dest.writeString(this.latGaoDe);
        dest.writeString(this.lonGaoDe);
        dest.writeString(this.groupRemark);
    }
}
