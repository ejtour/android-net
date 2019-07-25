package com.hll_sc_app.bean.report.req;


/**
 * 商品销售统计汇总
 */
public class ProductSaleAggregationReq extends  BaseReportReqParam{

    /**
     * 日期标志 1:本周 2:本月 3:上月 4:自定义
     */
    private byte dateFlag;

    public byte getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(byte dateFlag) {
        this.dateFlag = dateFlag;
    }
}
