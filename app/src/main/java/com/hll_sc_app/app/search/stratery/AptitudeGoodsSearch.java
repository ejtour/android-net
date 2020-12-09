package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.app.search.presenter.AptitudeGoodsSearchPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/25
 */
public class AptitudeGoodsSearch implements ISearchContract.ISearchStrategy {
    @Override
    public ISearchContract.ISearchPresenter getSearchPresenter() {
        return new AptitudeGoodsSearchPresenter();
    }

    @Override
    public String getEditHint() {
        return "请输入资质名称、商品名称进行搜索";
    }

    @Override
    public String getEmptyTip() {
        return "您可以通过资质名称、商品名称进行搜索";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_search_tips;
    }
}
