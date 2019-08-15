package com.hll_sc_app.app.marketingsetting.product.add;

/**
 * 活动类型
 *
 * @author zc
 */
public enum MarketingRule {
    /**/
    RULE_ZJ("4", "直降"),
    RULE_MZ("1", "买赠"),
    RULE_ZQ("5", "赠券"),
    RULE_MJ("2", "满减"),
    RULE_DZ("3", "打折");

    private String key;
    private String value;

    MarketingRule(String key, String value) {
        this.key = key;
        this.value = value;
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
    }}
