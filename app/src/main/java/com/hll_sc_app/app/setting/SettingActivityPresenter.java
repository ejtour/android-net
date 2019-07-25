package com.hll_sc_app.app.setting;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public class SettingActivityPresenter implements SettingActivityContract.ISaleUnitNameAddPresenter {
    private SettingActivityContract.ISaleUnitNameAddView mView;

    static SettingActivityPresenter newInstance() {
        return new SettingActivityPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(SettingActivityContract.ISaleUnitNameAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void logout() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("accessToken", UserConfig.accessToken())
            .create();
        UserService.INSTANCE.logout(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.logoutSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                }
            });
    }

    public static Observable<Object> logoutObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("accessToken", UserConfig.accessToken())
                .create();
        return UserService.INSTANCE
                .logout(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>());
    }
}
