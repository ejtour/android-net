package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 报价单详情接口数据
 *
 * @author zhuyingsong
 * @date 2019-07-08
 */
public class QuotationDetailResp {
    private List<QuotationDetailBean> records;

    public List<QuotationDetailBean> getRecords() {
        return records;
    }

    public void setRecords(List<QuotationDetailBean> records) {
        this.records = records;
    }
}
