package com.hll_sc_app.app.crm.settings;

import android.content.Context;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideApp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public class CrmSettingPresenter implements ICrmSettingContract.ICrmSettingPresenter {
    private ICrmSettingContract.ICrmSettingsView mView;

    @Override
    public void logout() {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.logoutSuccess();
            }
        };
        UserService.INSTANCE.logout(BaseMapReq.newBuilder()
                .put("accessToken", UserConfig.accessToken())
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
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
    public void register(ICrmSettingContract.ICrmSettingsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
