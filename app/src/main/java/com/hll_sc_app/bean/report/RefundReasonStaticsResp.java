package com.hll_sc_app.bean.report;

import java.util.List;

/**
 * 退货原因统计响应
 *
 * @author zc
 */
public class RefundReasonStaticsResp {
    private List<RefundReasonBean> records;
    private double totalAmount;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<RefundReasonBean> getRecords() {
        return records;
    }

    public void setRecords(List<RefundReasonBean> records) {
        this.records = records;
    }

    public static class RefundReasonBean {
        private double amount;
        private double proportion;
        //退款原因 1-供应商协商退款 2-产品质量退款 3-其他原因 4-商品质量有问题 5-商品与描述不符'', 6-退押金 7-商品外形品相差 8-商品有异物 9-商品腐烂变质 10-验货差异退款
        private int refundReason;
        private String refundReasonDesc;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getProportion() {
            return proportion;
        }

        public void setProportion(double proportion) {
            this.proportion = proportion;
        }

        public int getRefundReason() {
            return refundReason;
        }

        public void setRefundReason(int refundReason) {
            this.refundReason = refundReason;
        }

        public String getRefundReasonDesc() {
            return refundReasonDesc;
        }

        public void setRefundReasonDesc(String refundReasonDesc) {
            this.refundReasonDesc = refundReasonDesc;
        }
    }
}
