package com.hll_sc_app.app.goods;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
public class GoodsHomeFragmentPresenter implements GoodsHomeFragmentContract.IHomePresenter {
    private GoodsHomeFragmentContract.IHomeView mView;

    private GoodsHomeFragmentPresenter() {
    }

    public static GoodsHomeFragmentPresenter newInstance() {
        return new GoodsHomeFragmentPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(GoodsHomeFragmentContract.IHomeView view) {

    }
}
