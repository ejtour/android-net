package com.hll_sc_app.bean.report.resp.product;

/**
 * 商品统计（客户订货汇总）
 */
public class CustomerOrderAggregationResp {

    /**
     * 验货金额
     */
    private String inspectionAmount;
    /**
     * 验货数量
     */
    private String inspectionNum;
    /**
     * 采购商集团名称
     */
    private String purchaserName;
    /**
     * 采购商门店ID
     */
    private Long shopID;
    /**
     * 采购商门店名称
     */
    private String shopName;
    /**
     * 商品数
     */
    private Long skuNum;

    public String getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(String inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public String getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(String inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public Long getShopID() {
        return shopID;
    }

    public void setShopID(Long shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Long skuNum) {
        this.skuNum = skuNum;
    }
}
