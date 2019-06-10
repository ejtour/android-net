package com.hll_sc_app.app.user.register;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.bean.user.RegisterReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 注册页面-完善资料
 *
 * @author zhuyingsong
 * @date 2019/6/6
 */
public class RegisterComplementPresenter implements RegisterComplementContract.IRegisterComplementPresenter {
    private RegisterComplementContract.IRegisterComplementView mView;
    private List<CategoryItem> mList;

    static RegisterComplementPresenter newInstance() {
        return new RegisterComplementPresenter();
    }

    @Override
    public void start() {
        queryCategory(false);
    }

    @Override
    public void register(RegisterComplementContract.IRegisterComplementView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCategory(boolean show) {
        if (!CommonUtils.isEmpty(mList) && show) {
            mView.showCategoryWindow(mList);
            return;
        }
        UserService.INSTANCE.queryCategory(BaseMapReq.newBuilder().create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CategoryResp>() {
                @Override
                public void onSuccess(CategoryResp resp) {
                    mList = resp.getList();
                    if (show) {
                        mView.showCategoryWindow(mList);
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
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
