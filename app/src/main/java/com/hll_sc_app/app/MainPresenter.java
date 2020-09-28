package com.hll_sc_app.app;

import android.text.TextUtils;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.user.FollowQRResp;
import com.hll_sc_app.bean.user.FollowStatusResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Constants;

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
        GlobalPreference.putParam(Constants.ONLY_RECEIVE, false);
        User.queryOnlyReceive(mView, null);
        User.queryAuthList(mView.getOwner());
        if (!BuildConfig.isOdm && !UserConfig.crm() && !BuildConfig.isDebug) {
            User.queryFollowStatus(new SimpleObserver<FollowStatusResp>(mView) {
                @Override
                public void onSuccess(FollowStatusResp followStatusResp) {
                    if (TextUtils.isEmpty(followStatusResp.getOpenid())) {
                        User.queryFollowQR(new SimpleObserver<FollowQRResp>(mView) {
                            @Override
                            public void onSuccess(FollowQRResp followQRResp) {
                                mView.showFollowDialog(followQRResp.getQrcodeUrl());
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void register(IMainContract.IMainView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
