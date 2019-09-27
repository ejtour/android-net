package com.hll_sc_app.bean.event;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class ShopSearchEvent {

    private String name = "";
    private String shopMallId = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopMallId() {
        return shopMallId;
    }

    public void setShopMallId(String shopMallId) {
        this.shopMallId = shopMallId;
    }
}
