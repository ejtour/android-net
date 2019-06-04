package com.hll_sc_app.app.main;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
public class MainHomeFragmentPresenter implements MainHomeFragmentContract.IHomePresenter {
    private MainHomeFragmentContract.IHomeView mView;

    private MainHomeFragmentPresenter() {
    }

    public static MainHomeFragmentPresenter newInstance() {
        return new MainHomeFragmentPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(MainHomeFragmentContract.IHomeView view) {

    }
}
