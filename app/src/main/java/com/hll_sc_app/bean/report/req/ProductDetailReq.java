package com.hll_sc_app.bean.report.req;



public class ProductDetailReq extends BaseReportReqParam{

    /**
     * 商品分类ID
     */
    private String productCategoryIDs;
    /**
     * 商品分类级别（1：一级 2：二级 3：三级）
     */
    private byte   productCategoryLevel;
    /**
     * 商品关键字
     */
    private String productKeyword;

    public String getProductCategoryIDs() {
        return productCategoryIDs;
    }

    public void setProductCategoryIDs(String productCategoryIDs) {
        this.productCategoryIDs = productCategoryIDs;
    }

    public byte getProductCategoryLevel() {
        return productCategoryLevel;
    }

    public void setProductCategoryLevel(byte productCategoryLevel) {
        this.productCategoryLevel = productCategoryLevel;
    }

    public String getProductKeyword() {
        return productKeyword;
    }

    public void setProductKeyword(String productKeyword) {
        this.productKeyword = productKeyword;
    }
}
