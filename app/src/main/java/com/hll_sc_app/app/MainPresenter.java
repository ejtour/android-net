package com.hll_sc_app.app;

import android.text.TextUtils;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.user.FollowQRResp;
import com.hll_sc_app.bean.user.FollowStatusResp;
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
        User.queryOnlyReceive(mView, () -> mView.handleOnlyReceive());
        User.queryAuthList(mView.getOwner());
    }

    @Override
    public void register(IMainContract.IMainView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
