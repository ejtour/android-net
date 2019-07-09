package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 代仓签约详情
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class WarehouseDetailResp {
    private List<WarehouseShopBean> shops;

    public List<WarehouseShopBean> getShops() {
        return shops;
    }

    public void setShops(List<WarehouseShopBean> shops) {
        this.shops = shops;
    }
}
