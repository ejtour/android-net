package com.hll_sc_app.bean.event;

/**
 * 营销中心
 */
public class MarketingEvent {
    private String searchText;
    private boolean isRefreshProductList;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isRefreshProductList() {
        return isRefreshProductList;
    }

    public void setRefreshProductList(boolean refreshProductList) {
        isRefreshProductList = refreshProductList;
    }
}
