package com.hll_sc_app.app.crm.customer;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public class CustomerHelper {
    public static final String GOTO_KEY = "goto_key";
    public static final String VISIT_KEY = "visit_record";
    public static final int GOTO_INTENT = 1;
    public static final int GOTO_PARTNER_REGISTERED = 2;
    public static final int GOTO_PARTNER_UNREGISTERED = 3;
    public static final int GOTO_PLAN = 4;
    public static final int GOTO_RECORD = 5;

    public static String getCustomerType(int type) {
        return type == 1 ? "单店" : "连锁";
    }

    public static String getCustomerLevel(String level) {
        switch (level) {
            case "A":
                return "高价值客户";
            case "B":
                return "普通客户";
            case "C":
                return "低价值客户";
            default:
                return "";
        }
    }

    public static int getCustomerLevelFlag(String level){
        switch (level) {
            case "A":
                return R.drawable.ic_a;
            case "B":
                return R.drawable.ic_b;
            case "C":
                return R.drawable.ic_c;
            default:
                return 0;
        }
    }

    public static String getCustomerState(int state) {
        switch (state) {
            case 1:
                return "初步接触";
            case 2:
                return "意向客户";
            case 3:
                return "报价客户";
            case 4:
                return "合作客户";
            case 5:
                return "搁置客户";
            default:
                return "";
        }
    }

    public static String getCustomerSource(int source) {
        switch (source) {
            case 1:
                return "陌生拜访";
            case 2:
                return "老客户";
            case 3:
                return "客户介绍";
            case 4:
                return "市场活动";
            case 5:
                return "其他";
            default:
                return "";
        }
    }

    public static String getVisitGoal(int goal) {
        switch (goal) {
            case 1:
                return "客户保养";
            case 2:
                return "开发新客户";
            case 3:
                return "市场调查";
            case 4:
                return "了解竞争对手";
            default:
                return "";
        }
    }

    public static String getVisitCustomerType(int type) {
        switch (type) {
            case 1:
                return "意向客户";
            case 2:
                return "合作客户";
            default:
                return "";
        }
    }

    public static String getCustomerMaintainLevel(int level) {
        return level == 0 ? "门店级" : "集团级";
    }

    public static String getVisitWay(int way) {
        switch (way) {
            case 1:
                return "约访";
            case 2:
                return "电话拜访";
            default:
                return "";
        }
    }
}
