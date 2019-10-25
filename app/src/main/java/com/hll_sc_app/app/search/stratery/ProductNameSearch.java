package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

public class ProductNameSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入商品名称";
    }
}
