package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

public class SimpleSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "搜索";
    }
}
