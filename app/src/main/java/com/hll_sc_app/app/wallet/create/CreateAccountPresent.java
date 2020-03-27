package com.hll_sc_app.app.wallet.create;


import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;

public class CreateAccountPresent implements ICreateAccountContract.IPresent {
    private ICreateAccountContract.IView mView;

    public static CreateAccountPresent newInstance() {
        return new CreateAccountPresent();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(ICreateAccountContract.IView view) {
        mView = view;
    }

    @Override
    public void createAccount() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        WalletService.INSTANCE
                .createSettlementObject(BaseMapReq.newBuilder()
                        .put("groupID", userBean.getGroupID())
                        .put("groupName", mView.getGroupName())
                        .put("groupType", "0")
                        .create())
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
                    public void onSuccess(Object result) {
                        if (mView.isActive()) {
                           mView.createSuccess();
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (mView.isActive()) {
                            mView.showError(e);
                        }
                    }
                });

    }
}
