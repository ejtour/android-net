package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

public class CardManageListSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入卡号进行搜索";
    }
}
