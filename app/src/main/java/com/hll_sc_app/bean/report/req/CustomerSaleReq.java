package com.hll_sc_app.bean.report.req;


public class CustomerSaleReq extends  BaseReportReqParam {

    /**
     * 0-客户销售 1-客户销售门店
     */
    private byte actionType;
    /**
     * 时间
     */
    private long date;
    /**
     * 采购商集团ID
     */
    private long purchaserID;
    /**
     * 门店ID
     */
    private long shopID;

    public byte getActionType() {
        return actionType;
    }

    public void setActionType(byte actionType) {
        this.actionType = actionType;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(long purchaserID) {
        this.purchaserID = purchaserID;
    }

    public long getShopID() {
        return shopID;
    }

    public void setShopID(long shopID) {
        this.shopID = shopID;
    }
}
