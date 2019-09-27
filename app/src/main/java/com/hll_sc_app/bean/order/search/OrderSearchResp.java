package com.hll_sc_app.bean.order.search;

import com.hll_sc_app.bean.event.ShopSearchEvent;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class OrderSearchResp {
    private List<ShopSearchEvent> list;

    public List<ShopSearchEvent> getList() {
        return list;
    }

    public void setList(List<ShopSearchEvent> list) {
        this.list = list;
    }
}
