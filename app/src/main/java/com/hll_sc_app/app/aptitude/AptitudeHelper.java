package com.hll_sc_app.app.aptitude;

import com.hll_sc_app.bean.window.NameValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author <a href="mailto:xuezhixinhualala.com">Vixb</a>
 * since 2020/6/24
 */

public class AptitudeHelper {
    public static List<NameValue> getCompanyTypeList() {
        List<NameValue> list = new ArrayList<>();
        list.add(new NameValue("厂家", "1"));
        list.add(new NameValue("普通经销商", "2"));
        list.add(new NameValue("特约经销商", "3"));
        list.add(new NameValue("省级代理商", "4"));
        list.add(new NameValue("市级代理商", "5"));
        list.add(new NameValue("区级代理商", "6"));
        list.add(new NameValue("分销商或批发商", "7"));
        list.add(new NameValue("个体户", "8"));
        list.add(new NameValue("其他", "9"));
        return list;
    }

    public static String getCompanyType(int value) {
        switch (value) {
            case 1:
                return "厂家";
            case 2:
                return "普通经销商";
            case 3:
                return "特约经销商";
            case 4:
                return "省级代理商";
            case 5:
                return "市级代理商";
            case 6:
                return "区级代理商";
            case 7:
                return "分销商或批发商";
            case 8:
                return "个体户";
            case 9:
                return "其他";
            default:
                return "";
        }
    }

    public static List<NameValue> getStandardList() {
        List<NameValue> list = new ArrayList<>();
        list.add(new NameValue("国际标准", "1"));
        list.add(new NameValue("国家标准", "2"));
        list.add(new NameValue("行业标准", "3"));
        list.add(new NameValue("企业标准", "4"));
        return list;
    }

    public static String getStandard(int value) {
        switch (value) {
            case 1:
                return "国际标准";
            case 2:
                return "国家标准";
            case 3:
                return "行业标准";
            case 4:
                return "企业标准";
            default:
                return "";
        }
    }

    public static List<NameValue> getPayMethodList() {
        List<NameValue> list = new ArrayList<>();
        list.add(new NameValue("预付定金", "1"));
        list.add(new NameValue("分批结算", "2"));
        list.add(new NameValue("货到付款", "3"));
        list.add(new NameValue("先款后货", "4"));
        list.add(new NameValue("留质保金", "5"));
        list.add(new NameValue("周期对账", "6"));
        return list;
    }

    public static String getPayMethod(int value) {
        switch (value) {
            case 1:
                return "预付定金";
            case 2:
                return "分批结算";
            case 3:
                return "货到付款";
            case 4:
                return "先款后货";
            case 5:
                return "留质保金";
            case 6:
                return "周期对账";
            default:
                return "";
        }
    }

    public static List<String> getCertificationList() {
        return Arrays.asList("ISO 9001", "ISO 14001", "ISO 45001", "ISO 22000",
                "HACCP", "BRC", "IFS", "SQF", "DUTCH HACCP", "FSSC",
                "PAS 220", "AIB", "FPA", "Halal", "Kosher", "IP",
                "Non-GMO", "GAP", "Organics", "JAS", "EOS", "NOP",
                "MSC", "ACC", "FAMI-QS", "SC", "其他");
    }
}
