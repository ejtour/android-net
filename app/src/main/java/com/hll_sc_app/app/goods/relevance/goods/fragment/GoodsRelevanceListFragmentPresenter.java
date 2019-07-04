package com.hll_sc_app.app.goods.relevance.goods.fragment;

import com.hll_sc_app.citymall.util.CommonUtils;


/**
 * 第三方商品关联-采购商列表-关联商品列表-未关联、已关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public class GoodsRelevanceListFragmentPresenter implements GoodsRelevanceListFragmentContract.IGoodsRelevanceListPresenter {
    private GoodsRelevanceListFragmentContract.IGoodsRelevanceListView mView;

    static GoodsRelevanceListFragmentPresenter newInstance() {
        return new GoodsRelevanceListFragmentPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsRelevanceListFragmentContract.IGoodsRelevanceListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
