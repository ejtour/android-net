package com.hll_sc_app.bean.report.req;

/**
 * 商品销售统计top10
 */
public class ProductSaleTopReq extends  BaseReportReqParam{

    /**
     * 日期标志 1:本周 2:本月 3:上月 4:自定义
     */
    private byte dateFlag;
    /**
     * top10类型 1:销量 2: 金额
     */
    private byte type;


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(byte dateFlag) {
        this.dateFlag = dateFlag;
    }
}
