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
    private String status;

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
