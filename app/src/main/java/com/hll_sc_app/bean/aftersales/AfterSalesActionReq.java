package com.hll_sc_app.bean.aftersales;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class AfterSalesActionReq {
    /**
     * 1. 客服审核 2. 司机提货 3. 仓库收货  4. 财务审核  5. 客服驳回、司机取消 6. 采购商取消
     */
    private int orderAction;
    private String refundBillIDs;
    private String refundBillID;
    /**
     * 0 采购商审核 1 供应商审核
     */
    private int flag = 1;
    private int refundBillType;
    private int refundBillStatus;
    private String customAuditNote;
    /**
     * 自由退货客服审核必传的支付类型 1-货到付款 2- 账期支付
     */
    private String payType;
    /**
     * 驳回原因
     */
    private String refuseReason;
    /**
     * 退货明细列表，司机收货和仓库收货时传入
     */
    private List<ActionBean> refundBillDetailList;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public int getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(int orderAction) {
        this.orderAction = orderAction;
    }

    public String getRefundBillIDs() {
        return refundBillIDs;
    }

    public void setRefundBillIDs(String refundBillIDs) {
        this.refundBillIDs = refundBillIDs;
    }

    public String getRefundBillID() {
        return refundBillID;
    }

    public void setRefundBillID(String refundBillID) {
        this.refundBillID = refundBillID;
    }

    public int getRefundBillType() {
        return refundBillType;
    }

    public void setRefundBillType(int refundBillType) {
        this.refundBillType = refundBillType;
    }

    public int getRefundBillStatus() {
        return refundBillStatus;
    }

    public void setRefundBillStatus(int refundBillStatus) {
        this.refundBillStatus = refundBillStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getCustomAuditNote() {
        return customAuditNote;
    }

    public void setCustomAuditNote(String customAuditNote) {
        this.customAuditNote = customAuditNote;
    }

    public List<ActionBean> getRefundBillDetailList() {
        return refundBillDetailList;
    }

    public void setRefundBillDetailList(List<ActionBean> refundBillDetailList) {
        this.refundBillDetailList = refundBillDetailList;
    }

    public static class ActionBean {
        /**
         * 司机收货数量
         */
        private double deliveryNum;
        /**
         * 商品收货单价
         */
        private double deliveryPrice;
        /**
         * 仓库入库数量
         */
        private double inNum;
        /**
         * 商品入库单价
         */
        private double inPrice;
        private String refundBillDetailID;

        public double getDeliveryNum() {
            return deliveryNum;
        }

        public void setDeliveryNum(double deliveryNum) {
            this.deliveryNum = deliveryNum;
        }

        public double getDeliveryPrice() {
            return deliveryPrice;
        }

        public void setDeliveryPrice(double deliveryPrice) {
            this.deliveryPrice = deliveryPrice;
        }

        public double getInNum() {
            return inNum;
        }

        public void setInNum(double inNum) {
            this.inNum = inNum;
        }

        public double getInPrice() {
            return inPrice;
        }

        public void setInPrice(double inPrice) {
            this.inPrice = inPrice;
        }

        public String getRefundBillDetailID() {
            return refundBillDetailID;
        }

        public void setRefundBillDetailID(String refundBillDetailID) {
            this.refundBillDetailID = refundBillDetailID;
        }
    }
}
