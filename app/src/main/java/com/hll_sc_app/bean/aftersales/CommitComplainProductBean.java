package com.hll_sc_app.bean.aftersales;

/**
 * 提交投诉的products字段的内容
 * 用于json 字符串转换
 */
public class CommitComplainProductBean {
    private String detailID;
    private String productName;
    private String productSpec;
    private double productPrice;
    private double standardNum;
    private double adjustmentNum;
    private String imgUrl;

    public CommitComplainProductBean(AfterSalesDetailsBean bean) {
        this.productName = bean.getProductName();
        this.detailID = bean.getSubBillDetailID();
        this.productSpec = bean.getProductSpec();
        this.productPrice = bean.getProductPrice();
        this.standardNum = bean.getStandardNum();
        this.adjustmentNum = bean.getAdjustmentNum();
        this.imgUrl = bean.getImgUrl();


    }

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getStandardNum() {
        return standardNum;
    }

    public void setStandardNum(double standardNum) {
        this.standardNum = standardNum;
    }

    public double getAdjustmentNum() {
        return adjustmentNum;
    }

    public void setAdjustmentNum(double adjustmentNum) {
        this.adjustmentNum = adjustmentNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
