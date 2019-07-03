package com.hll_sc_app.bean.order.deliver;

import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class ModifyDeliverInfoReq {
    private String groupID;
    private String subBillID;
    private List<ProductBean> billDeliveryList;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public List<ProductBean> getBillDeliveryList() {
        return billDeliveryList;
    }

    public void setBillDeliveryList(List<ProductBean> billDeliveryList) {
        this.billDeliveryList = billDeliveryList;
    }

    public static class ProductBean {
        private double adjustmentAmount;
        private List<OrderDepositBean> depositList;
        private String detailID;
        private String productID;
        private double productNum;
        private double productPrice;
        private String unit;

        public static ProductBean convertFromOrderDetails(OrderDetailBean bean) {
            ProductBean productBean = new ProductBean();
            productBean.setAdjustmentAmount(bean.getAdjustmentAmount());
            productBean.setDepositList(bean.getDepositList());
            productBean.setDetailID(bean.getDetailID());
            productBean.setProductNum(bean.getAdjustmentNum());
            productBean.setProductPrice(bean.getProductPrice());
            productBean.setUnit(bean.getDeliverUnit());
            return productBean;
        }

        public double getAdjustmentAmount() {
            return adjustmentAmount;
        }

        public void setAdjustmentAmount(double adjustmentAmount) {
            this.adjustmentAmount = adjustmentAmount;
        }

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

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public double getProductNum() {
            return productNum;
        }

        public void setProductNum(double productNum) {
            this.productNum = productNum;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
