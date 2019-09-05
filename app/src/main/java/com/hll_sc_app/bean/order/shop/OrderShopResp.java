package com.hll_sc_app.bean.order.shop;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public class OrderShopResp {
    private double amount;
    private int billNum;
    private int shopNum;
    private List<OrderShopBean> orders;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public List<OrderShopBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderShopBean> orders) {
        this.orders = orders;
    }
}
