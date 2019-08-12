package com.hll_sc_app.bean.event;

/**
 * 营销设置搜索触发事件
 */
public class MarketingSearchEvent {
    private String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public MarketingSearchEvent(String searchText) {
        this.searchText = searchText;
    }
}
