package com.hll_sc_app.app.user.register;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.user.RegisterReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 注册页面-完善资料
 *
 * @author zhuyingsong
 * @date 2019/6/6
 */
public class RegisterComplementPresenter implements RegisterComplementContract.IRegisterComplementPresenter {
    private RegisterComplementContract.IRegisterComplementView mView;

    static RegisterComplementPresenter newInstance() {
        return new RegisterComplementPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(RegisterComplementContract.IRegisterComplementView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void toRegisterComplement(RegisterReq req) {
        BaseReq<RegisterReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        UserService.INSTANCE.register(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.registerComplementSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
