package com.hll_sc_app.app.wallet;

import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.wallet.WalletInfoReq;

/**
 *
 */
public class WalletPresent implements IWalletContract.IPresent {

    private IWalletContract.IView mView;

    public static WalletPresent newInstance() {
        return new WalletPresent();
    }

    @Override
    public void getWalletInfo(boolean isShowLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        WalletInfoReq req = new WalletInfoReq();
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("groupID",userBean.getGroupID())
                .put("groupType","0")
                .create();
        WalletService.INSTANCE
                .queryWalletStatus(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                            if (isShowLoading) {
                                mView.showLoading();
                            }
                        }
                )
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                })
                .subscribe(new BaseCallback<WalletInfo>() {
                    @Override
                    public void onSuccess(WalletInfo result) {
                        if (mView.isActive()) {
                            mView.getInfoSuccess(result);
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

    @Override
    public void start() {
    }

    @Override
    public void register(IWalletContract.IView view) {
        mView = view;
    }


}
