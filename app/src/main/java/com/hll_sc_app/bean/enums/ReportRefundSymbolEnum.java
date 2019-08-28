package com.hll_sc_app.bean.enums;

/**
 * 报表待退状态
 */
public enum ReportRefundSymbolEnum {

    wait_for_refund(1,"待退统计"),
    refunded(2,"退货统计 "),
    refund_customer(3,"退货客户统计"),
    refund_product(4,"退货商品统计");

    private int code;
    private String desc;

    ReportRefundSymbolEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
