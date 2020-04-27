package com.hll_sc_app.bean.report.customersettle;

import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

public class CustomerSettleDetailResp {
    private double paymentAmt;
    private double totalPrice;
    private double unPaymentAmt;
    private List<CustomReceiveListResp.RecordsBean> list;

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnPaymentAmt() {
        return unPaymentAmt;
    }

    public void setUnPaymentAmt(double unPaymentAmt) {
        this.unPaymentAmt = unPaymentAmt;
    }

    public List<CustomReceiveListResp.RecordsBean> getList() {
        return list;
    }

    public void setList(List<CustomReceiveListResp.RecordsBean> list) {
        this.list = list;
    }
}
