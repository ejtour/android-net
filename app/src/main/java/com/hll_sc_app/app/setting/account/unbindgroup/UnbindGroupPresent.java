package com.hll_sc_app.app.setting.account.unbindgroup;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.app.setting.SettingActivityPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.account.UnbindGroupReq;

/**
 * 解绑集团
 *
 * @author zc
 */
public class UnbindGroupPresent implements IUnbindGroupContract.IPresent {
    private IUnbindGroupContract.IView mView;

    static UnbindGroupPresent newInstance() {
        return new UnbindGroupPresent();
    }

    @Override
    public void unbindGroup() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseReq<UnbindGroupReq> baseReq = new BaseReq<>();
        UnbindGroupReq unbindGroupReq = new UnbindGroupReq();
        unbindGroupReq.setGroupID(userBean.getGroupID());
        unbindGroupReq.setGrouptype(UnbindGroupReq.PURCHASER);
        unbindGroupReq.setOriginLoginPhone(userBean.getLoginPhone());
        unbindGroupReq.setOriginVeriCode(mView.getIdentifyCode());
        baseReq.setData(unbindGroupReq);
        UserService.INSTANCE
                .unbindGroup(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                }).subscribe(new BaseCallback<Object>() {
            @Override
            public void onFailure(UseCaseException e) {
                if (mView.isActive()) {
                    mView.showToast(e.getMessage());
                }
            }

            @Override
            public void onSuccess(Object result) {
                if (mView.isActive()) {
                    mView.unbindSuccess();
                }
            }
        });


    }

    @Override
    public void logout() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            SettingActivityPresenter.logoutObservable()
                    .subscribe(new BaseCallback<Object>() {
                        @Override
                        public void onFailure(UseCaseException e) {
                            if (mView.isActive()){
                                mView.showToast(e.getMessage());
                            }
                        }

                        @Override
                        public void onSuccess(Object result) {
                           if(mView.isActive()){
                               mView.logoutSuccess();
                           }
                        }
                    });
        }

    }

    @Override
    public void start() {
        //no-op
    }

    @Override
    public void register(IUnbindGroupContract.IView view) {
        mView = view;
    }
}
