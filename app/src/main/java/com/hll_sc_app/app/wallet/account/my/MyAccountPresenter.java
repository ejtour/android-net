package com.hll_sc_app.app.wallet.account.my;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Wallet;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */

public class MyAccountPresenter implements IMyAccountContract.IMyAccountPresenter {
    private IMyAccountContract.IMyAccountView mView;

    public static MyAccountPresenter newInstance() {
        return new MyAccountPresenter();
    }

    private MyAccountPresenter() {
    }

    @Override
    public void start() {
        Wallet.queryAuthInfo(new SimpleObserver<AuthInfo>(mView) {
            @Override
            public void onSuccess(AuthInfo resp) {
                mView.handleAuthInfo(resp);
            }
        });
    }

    @Override
    public void register(IMyAccountContract.IMyAccountView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
