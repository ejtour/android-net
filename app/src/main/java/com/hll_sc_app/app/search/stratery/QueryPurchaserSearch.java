package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.app.search.presenter.QueryPurchaserPresenter;

/**
 * 请求合作采购商列表
 */
public class QueryPurchaserSearch implements ISearchContract.ISearchStrategy {
    @Override
    public ISearchContract.ISearchPresenter getSearchPresenter() {
        return new QueryPurchaserPresenter();
    }

    @Override
    public String getEditHint() {
        return "请输入客户集团名称进行搜索";
    }

    @Override
    public boolean isSearchByResult() {
        return true;
    }
}
