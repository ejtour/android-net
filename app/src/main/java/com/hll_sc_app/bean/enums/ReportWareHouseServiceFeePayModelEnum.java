package com.hll_sc_app.bean.enums;

public enum  ReportWareHouseServiceFeePayModelEnum {

    /**
     * 收费模式【0手工录入服务费3按照货值的百分比 4 按照配送数量收费 5 按货位收费】
     */
    TYPED_HANDING(0,"手工录入服务费"),
    GOODS_percentage(3,"按照货值的百分比"),
    delivery_number(4,"按照配送数量收费"),
    GOODS_POSITION(5,"按货位收费");

    private int code;
    private String desc;

    ReportWareHouseServiceFeePayModelEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据代仓收费code获取描述信息
     * @param code
     * @return
     */
    public static String getServiceFeePayModeDescByCode(int code){
        ReportWareHouseServiceFeePayModelEnum[] values = ReportWareHouseServiceFeePayModelEnum.values();
        for (ReportWareHouseServiceFeePayModelEnum value : values) {
            if(value.code == code){
                return value.desc;
            }
        }
        return "";
    }

}
