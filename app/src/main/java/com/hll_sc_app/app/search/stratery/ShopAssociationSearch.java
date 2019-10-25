package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.app.search.presenter.ShopSearchPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class ShopAssociationSearch implements ISearchContract.ISearchStrategy {
    @Override
    public ISearchContract.ISearchPresenter getSearchPresenter() {
        return new ShopSearchPresenter();
    }

    @Override
    public String getEditHint() {
        return "请输入采购商公司名称";
    }

    @Override
    public String getEmptyTip() {
        return "您可以输入客户名称查找采购商门店";
    }
}
