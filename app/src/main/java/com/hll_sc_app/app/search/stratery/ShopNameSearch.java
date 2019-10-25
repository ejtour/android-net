package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

public class ShopNameSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "您可以根据门店名称搜索";
    }
}
