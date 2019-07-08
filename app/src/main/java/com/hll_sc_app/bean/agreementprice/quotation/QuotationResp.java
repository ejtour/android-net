package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 报价单列表返回数据
 *
 * @author zhuyingsong
 * @date 2019-07-08
 */
public class QuotationResp {
    private List<QuotationBean> records;

    public List<QuotationBean> getRecords() {
        return records;
    }

    public void setRecords(List<QuotationBean> records) {
        this.records = records;
    }
}
