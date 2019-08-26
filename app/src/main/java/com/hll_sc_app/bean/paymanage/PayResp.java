package com.hll_sc_app.bean.paymanage;

import java.util.List;

/**
 * 查询默认支付方式列表成功
 *
 * @author zhuyingsong
 * @date 2019-08-12
 */
public class PayResp {
    private List<PayBean> records;

    public List<PayBean> getRecords() {
        return records;
    }

    public void setRecords(List<PayBean> records) {
        this.records = records;
    }
}
