package com.hll_sc_app.app.order;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
public class OrderHomeFragmentPresenter implements OrderHomeFragmentContract.IHomePresenter {
    private OrderHomeFragmentContract.IHomeView mView;

    private OrderHomeFragmentPresenter() {
    }

    public static OrderHomeFragmentPresenter newInstance() {
        return new OrderHomeFragmentPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(OrderHomeFragmentContract.IHomeView view) {

    }
}
