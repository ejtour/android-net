package com.hll_sc_app.app.warehouse.detail.showpaylist;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.paymanage.PayResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ShowPayListPresenter implements IShowPayListContract.IPresent {
    private IShowPayListContract.IView mView;

    private PayResp mAllPayList;

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
                .flatMap((Function<PayResp, ObservableSource<SettlementBean>>) payResp -> {
                    return getUsedPayListObservable(payResp);
                })
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<SettlementBean>() {
                    @Override
                    public void onSuccess(SettlementBean settlementBean) {
                        mView.showPayList(mAllPayList, settlementBean);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    private Observable<SettlementBean> getUsedPayListObservable(PayResp payResp) {
        mAllPayList = payResp;
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("supplyID", mView.getGroupId())
                .put("groupType", mView.isOwner() ? "0" : "1")
                .create();
        return CooperationPurchaserService.INSTANCE
                .querySettlementList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>());
    }
}
