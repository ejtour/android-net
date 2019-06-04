package com.hll_sc_app.app.mine;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
public class MineHomeFragmentPresenter implements MineHomeFragmentContract.IHomePresenter {
    private MineHomeFragmentContract.IHomeView mView;

    private MineHomeFragmentPresenter() {
    }

    public static MineHomeFragmentPresenter newInstance() {
        return new MineHomeFragmentPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(MineHomeFragmentContract.IHomeView view) {

    }
}
