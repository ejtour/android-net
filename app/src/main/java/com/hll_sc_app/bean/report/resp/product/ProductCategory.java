package com.hll_sc_app.bean.report.resp.product;

/**
 * 商品分类
 */
public class ProductCategory {

    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类金额
     */
    private float categoryOrderAmount;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public float getCategoryOrderAmount() {
        return categoryOrderAmount;
    }

    public void setCategoryOrderAmount(float categoryOrderAmount) {
        this.categoryOrderAmount = categoryOrderAmount;
    }
}
