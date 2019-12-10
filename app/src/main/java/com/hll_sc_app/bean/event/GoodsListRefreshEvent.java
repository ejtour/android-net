package com.hll_sc_app.bean.event;

/**
 * 商品管理
 */
public class GoodsListRefreshEvent {
    public GoodsListRefreshEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    private boolean isRefresh;

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
