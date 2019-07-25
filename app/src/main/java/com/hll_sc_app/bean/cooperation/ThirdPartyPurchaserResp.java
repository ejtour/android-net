package com.hll_sc_app.bean.cooperation;

import java.util.List;

/**
 * 第三方采购商
 *
 * @author zhuyingsong
 * @date 2019-07-24
 */
public class ThirdPartyPurchaserResp {
    private List<ThirdPartyPurchaserBean> records;
    private int total;

    public List<ThirdPartyPurchaserBean> getRecords() {
        return records;
    }

    public void setRecords(List<ThirdPartyPurchaserBean> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
