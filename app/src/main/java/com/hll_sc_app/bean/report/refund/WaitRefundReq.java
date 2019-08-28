package com.hll_sc_app.bean.report.refund;

/**
 * 待退请求参数封装
 */
public class WaitRefundReq {

    /**
     * 供应商集团ID
     */
    private String groupID;
    /**
     * 页码
     */
    private int    pageNum;
    /**
     * 页长
     */
    private int    pageSize;
    /**
     * 	检索字段
     */
    private String productName;
    /**
     * 	采购商集团ID
     */
    private String   purchaserID;
    /**
     * 	采购商门店ID
     */
    private String   shopID;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }
}
