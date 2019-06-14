package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品属性
 *
 * @author zhuyingsong
 * @date 2019-06-14
 */
public class ProductAttrBean {
    private String keyNote;
    private String widget;
    private String attrKey;
    private String tip;
    private String attrValue;
    private String id;
    private String pubAttrValue;
    private List<RegexBean> regexs;

    public String getKeyNote() {
        return keyNote;
    }

    public void setKeyNote(String keyNote) {
        this.keyNote = keyNote;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPubAttrValue() {
        return pubAttrValue;
    }

    public void setPubAttrValue(String pubAttrValue) {
        this.pubAttrValue = pubAttrValue;
    }

    public List<RegexBean> getRegexs() {
        return regexs;
    }

    public void setRegexs(List<RegexBean> regexs) {
        this.regexs = regexs;
    }

    public static class RegexBean {
        private String regex;
        private String tip;

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }
}


