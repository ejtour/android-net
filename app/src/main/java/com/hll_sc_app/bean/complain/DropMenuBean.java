package com.hll_sc_app.bean.complain;

import java.util.List;

/**
 * 下拉部门列表
 */
public class DropMenuBean {
    private String value;
    private String key;
    private String other;
    private List<DropMenuBean> children;

    public DropMenuBean(String value, String key) {
        this.value = value;
        this.key = key;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public List<DropMenuBean> getChildren() {
        return children;
    }

    public void setChildren(List<DropMenuBean> children) {
        this.children = children;
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
