package com.hll_sc_app.bean.stockmanage.purchaserorder;

/**
 * @author chukun
 * 采购商记录
 */
public class PurchaserOrderBean {

    private String[] billStatusDescArray = new String[]{"",
            "未审核", "已审核", "已提交", "配送中心审核", "部分已接单", "审核中", "已接单", "已发货",
            "被拒绝", "未结算", "已结算", "已驳回", "已提交至wms", "已提交至商城", "未推送至wms", "未推送至商城",
            "商城驳回订货单", "18已推送tms", "未推送tms", "已退货", "待收货", "已取消", "已处理", "已入库"
    };

    /**
     * 订单创建人
     */
    private String billCreateBy;
    /**
     * 订单创建时间
     */
    private String billCreateTime;
    /**
     * 采购日期
     */
    private String billDate;
    /**
     * 要求到货日期
     */
    private String billExecuteDate;
    /**
     * 订单id
     */
    private String billID;
    /**
     * 订单编号
     */
    private String billNo;
    /**
     * 订单备注
     */
    private String billRemark;
    /**
     * 订单状态,1-未审核,2-已审核,3-已提交,4-配送中心审核，5-部分已接单，6-审核中，7-已接单，8-已发货，
     * 9-被拒绝，10-未结算，11-已结算 ,12-已驳回 ，13-已提交至wms， 14-已提交至商城 ,15-未推送至wms 16-未推送至商城
     * 17:商城驳回订货单 18已推送tms 19未推送tms 20:已退货 21:待收货 22:已取消 23:已处理 24:已入库
     */
    private int billStatus;
    /**
     * 配送中心ID
     */
    private String demandID;
    /**
     * 配送中心名称
     */
    private String demandName;
    /**
     * 供应链集团ID
     */
    private String groupID;
    /**
     * 供应商ID
     */
    private String supplierID;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 调整前总金额
     */
    private double totalPrice;


    public String getBillCreateBy() {
        return billCreateBy;
    }

    public void setBillCreateBy(String billCreateBy) {
        this.billCreateBy = billCreateBy;
    }

    public String getBillCreateTime() {
        return billCreateTime;
    }

    public void setBillCreateTime(String billCreateTime) {
        this.billCreateTime = billCreateTime;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillExecuteDate() {
        return billExecuteDate;
    }

    public void setBillExecuteDate(String billExecuteDate) {
        this.billExecuteDate = billExecuteDate;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public String getDemandID() {
        return demandID;
    }

    public void setDemandID(String demandID) {
        this.demandID = demandID;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 订单状态,1-未审核,2-已审核,3-已提交,4-配送中心审核，5-部分已接单，6-审核中，7-已接单，8-已发货，
     * 9-被拒绝，10-未结算，11-已结算 ,12-已驳回 ，13-已提交至wms， 14-已提交至商城 ,15-未推送至wms 16-未推送至商城
     * 17:商城驳回订货单 18已推送tms 19未推送tms 20:已退货 21:待收货 22:已取消 23:已处理 24:已入库
     *
     * @return
     */
    public String getStatusDesc() {
        return billStatusDescArray[this.billStatus];
    }
}
