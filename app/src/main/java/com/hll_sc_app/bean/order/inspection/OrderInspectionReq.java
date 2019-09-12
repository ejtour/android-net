package com.hll_sc_app.bean.order.inspection;

import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class OrderInspectionReq {
    /**
     * 拒收标记位， 1-拒收
     */
    private int flag;
    private List<OrderInspectionItem> list;
    /**
     * 拒收说明
     */
    private String rejectExplain;
    /**
     * 拒收原因
     */
    private String rejectReason;
    /**
     * 拒收凭证
     */
    private String rejectVoucher;
    /**
     * 子订单ID
     */
    private String subBillID;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<OrderInspectionItem> getList() {
        return list;
    }

    public void setList(List<OrderInspectionItem> list) {
        this.list = list;
    }

    public String getRejectExplain() {
        return rejectExplain;
    }

    public void setRejectExplain(String rejectExplain) {
        this.rejectExplain = rejectExplain;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRejectVoucher() {
        return rejectVoucher;
    }

    public void setRejectVoucher(String rejectVoucher) {
        this.rejectVoucher = rejectVoucher;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public static class OrderInspectionItem {
        private List<OrderDepositBean> depositList;
        /**
         * 明细ID
         */
        private String detailID;
        /**
         * 验货数量 拒收传0
         */
        private double inspectionNum;
        /**
         * 验货单价
         */
        private double inspectionPrice;

        public List<OrderDepositBean> getDepositList() {
            return depositList;
        }

        public void setDepositList(List<OrderDepositBean> depositList) {
            this.depositList = depositList;
        }

        public String getDetailID() {
            return detailID;
        }

        public void setDetailID(String detailID) {
            this.detailID = detailID;
        }

        public double getInspectionNum() {
            return inspectionNum;
        }

        public void setInspectionNum(double inspectionNum) {
            this.inspectionNum = inspectionNum;
        }

        public double getInspectionPrice() {
            return inspectionPrice;
        }

        public void setInspectionPrice(double inspectionPrice) {
            this.inspectionPrice = inspectionPrice;
        }

        public static OrderInspectionItem copyFromDetailList(OrderDetailBean bean) {
            OrderInspectionItem item = new OrderInspectionItem();
            item.setDetailID(bean.getDetailID());
            item.setDepositList(bean.getDepositList());
            item.setInspectionNum(bean.getInspectionNum());
            item.setInspectionPrice(bean.getInspectionPrice());
            return item;
        }
    }
}
