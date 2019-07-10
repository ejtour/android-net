package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 比例模板返回数据
 *
 * @author zhuyingsong
 * @date 2019-07-10
 */
public class RatioTemplateResp {
    private List<RatioTemplateBean> records;
    private PageInfoBean pageInfo;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<RatioTemplateBean> getRecords() {
        return records;
    }

    public void setRecords(List<RatioTemplateBean> records) {
        this.records = records;
    }
}
