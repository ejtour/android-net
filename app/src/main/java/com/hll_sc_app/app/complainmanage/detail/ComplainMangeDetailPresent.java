package com.hll_sc_app.app.complainmanage.detail;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.ComplainStatusResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ComplainMangeDetailPresent implements IComplainMangeDetailContract.IPresent {
    private IComplainMangeDetailContract.IView mView;

    public static ComplainMangeDetailPresent newInstance() {
        return new ComplainMangeDetailPresent();
    }

    @Override
    public void start() {
        queryComplainStatus();
        queryComplainDetail();
    }

    @Override
    public void register(IComplainMangeDetailContract.IView view) {
        mView = view;
    }

    @Override
    public void queryComplainStatus() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("complaintID", mView.getComplaintID())
                .create();
        ComplainManageService.INSTANCE
                .queryComplainStatus(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ComplainStatusResp>() {
                    @Override
                    public void onSuccess(ComplainStatusResp complainStatusResp) {
                        mView.showComplainStatus(complainStatusResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }

    @Override
    public void queryComplainDetail() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("complaintID", mView.getComplaintID())
                .create();
        ComplainManageService.INSTANCE
                .queryComplainDetail(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ComplainDetailResp>() {
                    @Override
                    public void onSuccess(ComplainDetailResp complainDetailResp) {
                        mView.showComplainDetail(complainDetailResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }
}
