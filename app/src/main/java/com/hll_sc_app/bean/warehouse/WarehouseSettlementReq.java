package com.hll_sc_app.bean.warehouse;

import java.util.List;

/**
 * 获取结算方式列表
 *
 */
public class WarehouseSettlementReq {
    private List<GetSettlementWayListReqBean> getSettlementWayListReq;

    public List<GetSettlementWayListReqBean> getGetSettlementWayListReq() {
        return getSettlementWayListReq;
    }

    public void setGetSettlementWayListReq(List<GetSettlementWayListReqBean> getSettlementWayListReq) {
        this.getSettlementWayListReq = getSettlementWayListReq;
    }

    public static class GetSettlementWayListReqBean {
        private String groupID;
        private String purchaserID;
        private String shopID;

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
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
}
