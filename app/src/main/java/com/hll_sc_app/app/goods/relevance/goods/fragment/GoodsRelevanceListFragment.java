package com.hll_sc_app.app.goods.relevance.goods.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.base.BaseLazyFragment;

/**
 * 第三方商品关联-采购商列表-关联商品列表-未关联、已关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public class GoodsRelevanceListFragment extends BaseLazyFragment implements GoodsRelevanceListFragmentContract.IGoodsRelevanceListView {
    private GoodsRelevanceListFragmentPresenter mPresenter;

    public static GoodsRelevanceListFragment newInstance() {
        Bundle args = new Bundle();
        GoodsRelevanceListFragment fragment = new GoodsRelevanceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void initData() {

    }
}
