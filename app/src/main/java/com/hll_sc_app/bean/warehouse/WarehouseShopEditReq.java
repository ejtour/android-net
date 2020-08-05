package com.hll_sc_app.bean.warehouse;

import com.hll_sc_app.base.utils.UserConfig;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/27
 */

public class WarehouseShopEditReq {
    private String actionType = "supplierOperate";
    private List<String> deleteShopIds;
    private String groupID;
    private String purchaserID;
    private List<String> shopIds;
    private int warehouseType;

    public WarehouseShopEditReq() {
        groupID = UserConfig.getGroupID();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public List<String> getDeleteShopIds() {
        return deleteShopIds;
    }

    public void setDeleteShopIds(List<String> deleteShopIds) {
        this.deleteShopIds = deleteShopIds;
    }

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

    public List<String> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<String> shopIds) {
        this.shopIds = shopIds;
    }

    public int getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(int warehouseType) {
        this.warehouseType = warehouseType;
    }
}
