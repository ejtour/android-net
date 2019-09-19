package com.hll_sc_app.app.complainmanage.history;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.ComplainHistoryResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ComplainHistoryPresent implements IComplainHistoryContract.IPresent {
    private IComplainHistoryContract.IView mView;

    public static ComplainHistoryPresent newInstance() {
        return new ComplainHistoryPresent();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(IComplainHistoryContract.IView view) {
        mView = view;
    }

    @Override
    public void queryHistory() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("complaintID", mView.getComplaintID())
                .create();
        ComplainManageService.INSTANCE
                .queryComplainHistory(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ComplainHistoryResp>() {
                    @Override
                    public void onSuccess(ComplainHistoryResp complainHistoryResp) {
                        mView.queryHistorySucess(complainHistoryResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }

}
