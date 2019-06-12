package com.hll_sc_app.bean.order;

import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderResp {
    private String couponDiscountRuleName;
    private String couponDiscountName;
    private int userID;
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
    private int salesManID;
    private int subBillType;
    private int shipperID;
    private String payOrderNo;
    private int masterBillID;
    private double adjustmentDiscountSubAmount;
    private String supplyBillNo;
    private String acceptTime;
    private String shipperName;
    private int paymentWay;
    private int couponID;
    private int nextDayDelivery;
    private String groupName;
    private String checkTime;
    private int inspectionDepositTotalAmount;
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
    private int supplyShopID;
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
    private int orderDepositTotalAmount;
    private int orderDiscountRuleID;
    private int adjustmentDepositTotalAmount;
    private int shopID;
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
    private int purchaserID;
    private int settlementStatus;
    private int subBillStatus;
    private double inspectionDiscountSubAmount;
    private String billUpdateTime;
    private int orderDiscountID;
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
    private boolean mCanSelect;
    private String driverId;
    private String driverName;
    private String mobilePhone;
    private String plateNumber;
    private List<Integer> buttonList;
    private int receiptRemaining;
    private List<OrderDetailBean> billDetailList;

    public String getTargetAddress() {
        return deliverType == 2 ? houseAddress : receiverAddress;
    }

    public String getTargetExecuteDate() {
        String HhMm = "2400";
        String formatDateStr = CalendarUtils.getDateFormatString(subBillExecuteDate, Constants.FORMAT_YYYY_MM_DD_HH_MM,
                Constants.FORMAT_YYYY_MM_DD_HH_MM_DASH);
        if (subBillExecuteEndDate.endsWith(HhMm)) return formatDateStr + "-" + "24:00";
        return formatDateStr + "-" + CalendarUtils.getDateFormatString(subBillExecuteEndDate,
                Constants.FORMAT_YYYY_MM_DD_HH_MM, "HH:mm");
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

    public int getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(int salesManID) {
        this.salesManID = salesManID;
    }

    public int getSubBillType() {
        return subBillType;
    }

    public void setSubBillType(int subBillType) {
        this.subBillType = subBillType;
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

    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
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

    public int getInspectionDepositTotalAmount() {
        return inspectionDepositTotalAmount;
    }

    public void setInspectionDepositTotalAmount(int inspectionDepositTotalAmount) {
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

    public int getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(int supplyShopID) {
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

    public int getOrderDepositTotalAmount() {
        return orderDepositTotalAmount;
    }

    public void setOrderDepositTotalAmount(int orderDepositTotalAmount) {
        this.orderDepositTotalAmount = orderDepositTotalAmount;
    }

    public int getOrderDiscountRuleID() {
        return orderDiscountRuleID;
    }

    public void setOrderDiscountRuleID(int orderDiscountRuleID) {
        this.orderDiscountRuleID = orderDiscountRuleID;
    }

    public int getAdjustmentDepositTotalAmount() {
        return adjustmentDepositTotalAmount;
    }

    public void setAdjustmentDepositTotalAmount(int adjustmentDepositTotalAmount) {
        this.adjustmentDepositTotalAmount = adjustmentDepositTotalAmount;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
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

    public int getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(int purchaserID) {
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

    public int getOrderDiscountID() {
        return orderDiscountID;
    }

    public void setOrderDiscountID(int orderDiscountID) {
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

    public boolean isCanSelect() {
        return mCanSelect;
    }

    public void setCanSelect(boolean canSelect) {
        mCanSelect = canSelect;
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
}
