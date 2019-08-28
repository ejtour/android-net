package com.hll_sc_app.bean.marketingsetting;

/**
 * 促销状态
 */
public class MarketingStatusBean {
    private String value;
    private String key;

    public MarketingStatusBean(String value, String key) {
        this.value = value;
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
