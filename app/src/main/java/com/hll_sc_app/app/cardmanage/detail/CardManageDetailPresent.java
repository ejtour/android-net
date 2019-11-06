package com.hll_sc_app.app.cardmanage.detail;

import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CardManageDetailPresent implements ICardManageDetailContract.IPresent {
    private ICardManageDetailContract.IView mView;

    public static CardManageDetailPresent newInstance() {
        return new CardManageDetailPresent();
    }

    @Override
    public void register(ICardManageDetailContract.IView view) {
        mView = view;

    }

    @Override
    public void changeCardStatus(int status, String remark) {
        CardManageService.INSTANCE
                .changeCardStatus(BaseMapReq.newBuilder()
                        .put("cardNo", mView.getCardBean().getCardNo())
                        .put("cardStatus", String.valueOf(status))
                        .put("id", mView.getCardBean().getId())
                        .put("remark", remark)
                        .create()
                ).compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.changeSuccess(status);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }
}
