package com.hll_sc_app.bean.warehouse;

import java.util.List;

/**
 * 代仓编辑合作采购商
 *
 * @author zhuyingsong
 * @date 2019-08-07
 */
public class WarehousePurchaserEditReq {
    private String actionType;
    private String groupID;
    private String warehouseID;
    private List<PurchaserId> purchaserList;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public List<PurchaserId> getPurchaserList() {
        return purchaserList;
    }

    public void setPurchaserList(List<PurchaserId> purchaserList) {
        this.purchaserList = purchaserList;
    }

    public static class PurchaserId {

        private String purchaserID;

        public PurchaserId(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }
    }
}
