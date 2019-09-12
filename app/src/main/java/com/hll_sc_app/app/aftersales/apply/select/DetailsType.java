package com.hll_sc_app.app.aftersales.apply.select;

/**
 * 明细列表类型
 */
public enum DetailsType {
    COMMODITY("选择退货商品", "可退货数：", "退货："),
    DEPOSIT("选择押金商品", "退押金：", "退回：");
    private String title;
    private String label;
    private String editPrefix;

    public String getTitle() {
        return title;
    }

    public String getLabel() {
        return label;
    }

    public String getEditPrefix() {
        return editPrefix;
    }

    DetailsType(String title, String label, String editPrefix) {
        this.title = title;
        this.label = label;
        this.editPrefix = editPrefix;
    }
}
