package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

public class NameSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入名称进行查询";
    }
}
