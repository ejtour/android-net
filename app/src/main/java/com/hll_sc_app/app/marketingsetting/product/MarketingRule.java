package com.hll_sc_app.app.marketingsetting.product;

import android.text.TextUtils;

/**
 * 活动类型
 *
 * @author zc
 */
public enum MarketingRule {
    /*商品营销增加*/
    RULE_ZJ("4", "直降"),
    RULE_MZ("1", "买赠"),//商品买赠 订单叫满赠
    RULE_ZQ("5", "赠券"),
    RULE_MJ("6", "满减"),
    RULE_DZ("7", "打折"),
    RULE_MANZHE("3", "满折"),
    RULE_MJ_ORDER("2", "满减"),
    RULE_NULL("", "");


    private String key;
    private String value;

    MarketingRule(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static MarketingRule getRuleEnum(int type) {
        String sType = type + "";
        if (TextUtils.equals(sType, RULE_ZJ.getKey())) {
            return RULE_ZJ;
        } else if (TextUtils.equals(sType, RULE_MZ.getKey())) {
            return RULE_MZ;
        } else if (TextUtils.equals(sType, RULE_ZQ.getKey())) {
            return RULE_ZQ;
        } else if (TextUtils.equals(sType, RULE_DZ.getKey())) {
            return RULE_DZ;
        } else if (TextUtils.equals(sType, RULE_MJ.getKey())) {
            return RULE_MJ;
        } else if (TextUtils.equals(sType, RULE_MANZHE.getKey())) {
            return RULE_MANZHE;
        } else if (TextUtils.equals(sType, RULE_MJ_ORDER.getKey())) {
            return RULE_MJ_ORDER;
        } else {
            return RULE_NULL;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
