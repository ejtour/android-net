package com.hll_sc_app.bean.order.settle;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public class CashierResp {
    /**
     * 刷卡支付
     */
    public static final String PAY_TYPE_SWIPE = "3";
    /**
     * 现金支付
     */
    public static final String PAY_TYPE_CASH = "4";
    /**
     * 微信扫码
     */
    public static final String PAY_TYPE_WECHAT_QR = "3";
    /**
     * 支付宝扫码
     */
    public static final String PAY_TYPE_ALIPAY_QR = "4";

    /**
     * 收银台地址（支付方式为微信，支付宝时 返回二维码url） 如果是微信公众号 或者app支付则为json串
     */
    private String cashierUrl;
    /**
     * 订单编号
     */
    private String orderKey;
    /**
     * 预支付单号
     */
    private String payOrderNo;
    /**
     * 预支付单号
     */
    private double totalAmount;

    public String getCashierUrl() {
        return cashierUrl;
    }

    public void setCashierUrl(String cashierUrl) {
        this.cashierUrl = cashierUrl;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
