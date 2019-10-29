package com.hll_sc_app.app.setting.account.unbindmainaccount;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.account.UnbindMainAccountReq;
import com.hll_sc_app.rest.User;

/**
 * 更改集团绑定手机号
 *
 * @author zc
 */
public class UnbindMainAccountPresent implements IUnbindMainAccountContract.IPresent {
    private IUnbindMainAccountContract.IView mView;

    static UnbindMainAccountPresent newInstance() {
        return new UnbindMainAccountPresent();
    }

    @Override
    public void unBindMainAccount(UnbindMainAccountReq req) {
        BaseReq<UnbindMainAccountReq> baseReq = new BaseReq<>();
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        req.setGroupID(userBean.getGroupID());
        baseReq.setData(req);
        UserService.INSTANCE
                .unBindMainAccount(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                })
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(Object result) {
                        mView.getUnbindResult(true);
                    }
                });
    }

    /**
     * 登出操作
     */
    @Override
    public void logout() {
        User.logout(new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.logoutSuccess();
            }

            @Override
            public void onFailure(UseCaseException e) {
                super.onFailure(e);
                /*在这里，失败也要调用它*/
                mView.logoutSuccess();
            }
        });
    }

    @Override
    public void start() {
        //no-op

    }

    @Override
    public void register(IUnbindMainAccountContract.IView view) {
        mView = view;
    }
}
