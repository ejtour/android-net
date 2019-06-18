package com.hll_sc_app.bean.order;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2019/6/18
 */

public class TransferBean {
    private long actionTime;
    private double totalPrice;
    private int billSource;
    private String allotID;
    private String shopmallBillNo;
    private String allotName;
    private long billCreateTime;
    private int shipperType;
    private int payType;
    private int purchaserID;
    private double amountPaid;
    private long billExecuteEndTime;
    private String orgCode;
    private int plateSupplierID;
    private String purchaseBillNo;
    private int action;
    private String billRemark;
    private String cancelBy;
    private String failReason;
    private int id;
    private String cancelReason;
    private String billNo;
    private String billCreateBy;
    private int isPay;
    private String payOrderNo;
    private String receiverName;
    private String groupID;
    private long acceptTime;
    private String receiverMobile;
    private int billDate;
    private int isShopMall;
    private int paymentWay;
    private long billExecuteTime;
    private String receiverAddress;
    private String acceptBy;
    private String groupName;
    private int operateModel;
    private long createTime;
    private int shopID;
    private String plateSupplierName;
    private String ordererMobile;
    private int homologous;
    private int status;

    OrderResp convertToOrderResp() {
        OrderResp resp = new OrderResp();
        resp.setActionTime(String.valueOf(actionTime));
        resp.setTotalAmount(totalPrice);
        resp.setBillSource(billSource);
        resp.setShopID(allotID);
        resp.setShopName(allotName);
        resp.setSubBillCreateTime(String.valueOf(billCreateTime));
        resp.setShipperType(shipperType);
        resp.setPayType(payType);
        resp.setPurchaserID(purchaserID);
        resp.setAmountPaid(amountPaid);
        resp.setSubBillExecuteEndDate(String.valueOf(billExecuteEndTime));
        resp.setSubBillExecuteDate(String.valueOf(billExecuteTime));
        resp.setSubBillNo(purchaseBillNo);
        resp.setPurchaserName(groupName);
        return resp;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBillSource() {
        return billSource;
    }

    public void setBillSource(int billSource) {
        this.billSource = billSource;
    }

    public String getAllotID() {
        return allotID;
    }

    public void setAllotID(String allotID) {
        this.allotID = allotID;
    }

    public String getShopmallBillNo() {
        return shopmallBillNo;
    }

    public void setShopmallBillNo(String shopmallBillNo) {
        this.shopmallBillNo = shopmallBillNo;
    }

    public String getAllotName() {
        return allotName;
    }

    public void setAllotName(String allotName) {
        this.allotName = allotName;
    }

    public long getBillCreateTime() {
        return billCreateTime;
    }

    public void setBillCreateTime(long billCreateTime) {
        this.billCreateTime = billCreateTime;
    }

    public int getShipperType() {
        return shipperType;
    }

    public void setShipperType(int shipperType) {
        this.shipperType = shipperType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(int purchaserID) {
        this.purchaserID = purchaserID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public long getBillExecuteEndTime() {
        return billExecuteEndTime;
    }

    public void setBillExecuteEndTime(long billExecuteEndTime) {
        this.billExecuteEndTime = billExecuteEndTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getPlateSupplierID() {
        return plateSupplierID;
    }

    public void setPlateSupplierID(int plateSupplierID) {
        this.plateSupplierID = plateSupplierID;
    }

    public String getPurchaseBillNo() {
        return purchaseBillNo;
    }

    public void setPurchaseBillNo(String purchaseBillNo) {
        this.purchaseBillNo = purchaseBillNo;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public long getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public int getBillDate() {
        return billDate;
    }

    public void setBillDate(int billDate) {
        this.billDate = billDate;
    }

    public int getIsShopMall() {
        return isShopMall;
    }

    public void setIsShopMall(int isShopMall) {
        this.isShopMall = isShopMall;
    }

    public int getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(int paymentWay) {
        this.paymentWay = paymentWay;
    }

    public long getBillExecuteTime() {
        return billExecuteTime;
    }

    public void setBillExecuteTime(long billExecuteTime) {
        this.billExecuteTime = billExecuteTime;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getAcceptBy() {
        return acceptBy;
    }

    public void setAcceptBy(String acceptBy) {
        this.acceptBy = acceptBy;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(int operateModel) {
        this.operateModel = operateModel;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getPlateSupplierName() {
        return plateSupplierName;
    }

    public void setPlateSupplierName(String plateSupplierName) {
        this.plateSupplierName = plateSupplierName;
    }

    public String getOrdererMobile() {
        return ordererMobile;
    }

    public void setOrdererMobile(String ordererMobile) {
        this.ordererMobile = ordererMobile;
    }

    public int getHomologous() {
        return homologous;
    }

    public void setHomologous(int homologous) {
        this.homologous = homologous;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
