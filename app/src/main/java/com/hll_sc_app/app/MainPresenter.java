package com.hll_sc_app.app;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/22
 */
class MainPresenter implements IMainContract.IMainPresenter {
    private IMainContract.IMainView mView;

    public static MainPresenter newInstance() {
        return new MainPresenter();
    }

    private MainPresenter() {
    }

    @Override
    public void start() {
//        User.queryGroupParam("7,37,38,40",mView, () -> mView.handleOnlyReceive());
        User.queryGroupParam("7,37,38",mView, () -> mView.handleOnlyReceive());
        User.queryAuthList(mView.getOwner());
    }

    @Override
    public void register(IMainContract.IMainView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
