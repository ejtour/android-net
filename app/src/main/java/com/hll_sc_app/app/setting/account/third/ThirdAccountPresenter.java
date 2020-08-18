package com.hll_sc_app.app.setting.account.third;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/13
 */

class ThirdAccountPresenter implements IThirdAccountContract.IThirdAccountPresenter {
    private IThirdAccountContract.IThirdAccountView mView;

    public static ThirdAccountPresenter newInstance() {
        return new ThirdAccountPresenter();
    }

    private ThirdAccountPresenter() {
    }

    @Override
    public void unbind(int type) {
        User.unbindAccount(type, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object object) {
                mView.success();
            }
        });
    }

    @Override
    public void register(IThirdAccountContract.IThirdAccountView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
