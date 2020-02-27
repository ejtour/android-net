package com.hll_sc_app.app.warehouse.detail.showpaylist;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.paymanage.PayResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ShowPayListPresenter implements IShowPayListContract.IPresent {
    private IShowPayListContract.IView mView;

    public static ShowPayListPresenter newInstance() {
        return new ShowPayListPresenter();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(IShowPayListContract.IView view) {
        mView = view;
    }

    @Override
    public void getAllPayList() {
        CooperationPurchaserService.INSTANCE
                .querySettlementMethodList(BaseMapReq.newBuilder().create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<PayResp>() {
                    @Override
                    public void onSuccess(PayResp payResp) {
                        mView.showPayList(payResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
