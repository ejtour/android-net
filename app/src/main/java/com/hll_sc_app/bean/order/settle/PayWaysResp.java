package com.hll_sc_app.bean.order.settle;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public class PayWaysResp {
    public static final String PAY_TYPE_ALIPAY_ONLINE = "1";
    public static final String PAY_TYPE_WECHAT_ONLINE = "2";
    public static final String PAY_TYPE_ALIPAY_OFFLINE = "7";
    public static final String PAY_TYPE_WECHAT_OFFLINE = "8";
    public static final String PAY_TYPE_SWIPE = "9";
    public static final String PAY_TYPE_CASH = "10";

    private List<PayWayBean> records;

    public List<PayWayBean> getRecords() {
        return records;
    }

    public void setRecords(List<PayWayBean> records) {
        this.records = records;
    }
}
