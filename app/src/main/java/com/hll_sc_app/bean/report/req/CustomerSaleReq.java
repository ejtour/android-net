package com.hll_sc_app.bean.report.req;


public class CustomerSaleReq extends  BaseReportReqParam {

    /**
     * 0-客户销售 1-客户销售门店
     */
    private int actionType;
    /**
     * 时间
     */
    private String date;
    /**
     * 采购商集团ID
     */
    private String purchaserID;
    /**
     * 门店ID
     */
    private String shopID;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
