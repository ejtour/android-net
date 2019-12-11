package com.hll_sc_app.bean.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基本时间的枚举
 */
public enum TimeTypeEnum {

    //1:日，2：周，3：月，4：年
    DAY(1,"day"),
    WEEK(2,"week"),
    MONTH(3,"month"),
    YEAR(4,"year");

    private int code;
    private String desc;

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
    }

    TimeTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
