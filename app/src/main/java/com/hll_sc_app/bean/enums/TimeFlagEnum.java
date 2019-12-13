package com.hll_sc_app.bean.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 运营平台首页订单的时间枚举
 */
public enum TimeFlagEnum {

    //0今天，1昨天，2本周，3上周，4本月，5上月，6自定义，7今年
    TODAY(0,"今日"),
    YESTERDAY(1,"昨日"),
    CURRENTWEEK(2,"本周"),
    LASTWEEK(3,"上周"),
    CURRENTMONTH(4,"本月"),
    LASTMONTH(5,"上月"),
    CUSTOMDEFINE(6,"自定义"),
    CURRENTYEAR(7,"今年"),
    NEARLY_SEVEN_DAYS(8,"近7天"),
    NEARLY_THIRTY_DAYS(9,"近30天"),
    NEARLY_NINETY_DAYS(10,"近90天");

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

    TimeFlagEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
