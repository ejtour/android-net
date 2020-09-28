package com.hll_sc_app.app.setting;

import android.content.Context;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.glide.GlideApp;
import com.hll_sc_app.bean.user.FollowQRResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public class SettingPresenter implements SettingContract.ISettingPresenter {
    private SettingContract.ISettingView mView;

    static SettingPresenter newInstance() {
        return new SettingPresenter();
    }

    @Override
    public void register(SettingContract.ISettingView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void logout() {
        User.logout(new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.logoutSuccess();
            }
        });
    }

    @Override
    public void cleanCache() {
        Observable.timer(0, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(aLong -> {
                    GlideApp.get((Context) mView).clearDiskCache();
                    return aLong;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.startClean())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(o -> mView.cleanSuccess());
    }

    @Override
    public void queryFollowQR() {
        User.queryFollowQR(new SimpleObserver<FollowQRResp>(mView) {
            @Override
            public void onSuccess(FollowQRResp followQRResp) {
                mView.showFollowDialog(followQRResp.getQrcodeUrl());
            }
        });
    }
}
