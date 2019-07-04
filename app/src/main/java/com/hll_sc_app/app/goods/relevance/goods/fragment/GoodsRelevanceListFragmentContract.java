package com.hll_sc_app.app.goods.relevance.goods.fragment;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;


/**
 * 第三方商品关联-采购商列表-关联商品列表-未关联、已关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public interface GoodsRelevanceListFragmentContract {

    interface IGoodsRelevanceListView extends ILoadView {

    }

    interface IGoodsRelevanceListPresenter extends IPresenter<IGoodsRelevanceListView> {

    }
}
