package com.hll_sc_app.bean.report.req;



public class CustomerOrderReq extends BaseReportReqParam {

    /**
     * 采购商门店列表，多个间用英文逗号隔开
     */
    private String shopIDs;

    public String getShopIDs() {
        return shopIDs;
    }

    public void setShopIDs(String shopIDs) {
        this.shopIDs = shopIDs;
    }
}
