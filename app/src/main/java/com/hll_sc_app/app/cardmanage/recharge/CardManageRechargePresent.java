package com.hll_sc_app.app.cardmanage.recharge;

import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CardManageRechargePresent implements ICardManageRechargeContract.IPresent {

    private ICardManageRechargeContract.IView mView;

    public static CardManageRechargePresent newInstance() {
        return new CardManageRechargePresent();
    }

    @Override
    public void register(ICardManageRechargeContract.IView view) {
        mView = view;
    }


    @Override
    public void recharge(String cashBalance, String giftBalance, String remark) {
        CardManageService.INSTANCE
                .recharge(BaseMapReq.newBuilder()
                        .put("cashBalance", cashBalance)
                        .put("giftBalance", giftBalance)
                        .put("remark", remark)
                        .put("id", mView.getCardId())
                        .create()
                )
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.rechargeSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
