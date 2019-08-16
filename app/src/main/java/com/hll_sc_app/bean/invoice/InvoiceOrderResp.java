package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class InvoiceOrderResp {
    private double invoinceAmount;
    private double orderAmount;
    private double refundAmount;
    private int total;
    private List<InvoiceOrderBean> list;

    public double getInvoinceAmount() {
        return invoinceAmount;
    }

    public void setInvoinceAmount(double invoinceAmount) {
        this.invoinceAmount = invoinceAmount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<InvoiceOrderBean> getList() {
        return list;
    }

    public void setList(List<InvoiceOrderBean> list) {
        this.list = list;
    }
}
