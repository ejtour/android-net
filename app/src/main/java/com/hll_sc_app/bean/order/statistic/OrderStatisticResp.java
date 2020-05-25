package com.hll_sc_app.bean.order.statistic;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticResp {
    private int shopNum;
    private double totalAmount;
    private int totalNum;
    private List<OrderStatisticBean> list;

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<OrderStatisticBean> getList() {
        return list;
    }

    public void setList(List<OrderStatisticBean> list) {
        this.list = list;
    }
}
