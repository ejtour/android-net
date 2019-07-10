package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 报价单新增参数
 *
 * @author zhuyingsong
 * @date 2019-07-10
 */
public class QuotationReq {
    private QuotationBean quotation;
    private List<QuotationDetailBean> list;

    public QuotationBean getQuotation() {
        return quotation;
    }

    public void setQuotation(QuotationBean quotation) {
        this.quotation = quotation;
    }

    public List<QuotationDetailBean> getList() {
        return list;
    }

    public void setList(List<QuotationDetailBean> list) {
        this.list = list;
    }
}
