package com.hll_sc_app.bean.warehouse;

import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 代仓签约详情
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class WarehouseDetailResp {
    private List<WarehouseShopBean> shops;
    private PurchaserBean purchaserInfo;
    private PurchaserBean groupInfo;
    private String status;
    private String returnAudit;
    private int warehouseActive;

    public int getWarehouseActive() {
        return warehouseActive;
    }

    public void setWarehouseActive(int warehouseActive) {
        this.warehouseActive = warehouseActive;
    }

    public PurchaserBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(PurchaserBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public String getReturnAudit() {
        return returnAudit;
    }

    public void setReturnAudit(String returnAudit) {
        this.returnAudit = returnAudit;
    }

    public PurchaserBean getPurchaserInfo() {
        return purchaserInfo;
    }

    public void setPurchaserInfo(PurchaserBean purchaserInfo) {
        this.purchaserInfo = purchaserInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WarehouseShopBean> getShops() {
        return shops;
    }

    public void setShops(List<WarehouseShopBean> shops) {
        this.shops = shops;
    }
}
