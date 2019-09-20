package com.hll_sc_app.bean.complain;

/**
 * 下拉部门列表
 */
public class DepartmentsBean {
    private String value;
    private String key;

    public DepartmentsBean(String value, String key) {
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
