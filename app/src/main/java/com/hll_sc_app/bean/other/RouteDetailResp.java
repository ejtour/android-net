package com.hll_sc_app.bean.other;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public class RouteDetailResp {
    private int shopNum;
    private int billNum;
    private List<RouteDetailBean> records;

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public List<RouteDetailBean> getRecords() {
        return records;
    }

    public void setRecords(List<RouteDetailBean> records) {
        this.records = records;
    }
}
