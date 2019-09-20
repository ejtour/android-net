package com.hll_sc_app.app.complainmanage.sendcomplainreply;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SendComplainReplyPresent implements ISendComplainReplyContract.IPresent {
    private ISendComplainReplyContract.IView mView;

    public static SendComplainReplyPresent newInstance() {
        return new SendComplainReplyPresent();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(ISendComplainReplyContract.IView view) {
        mView = view;
    }

    @Override
    public void sendComplainReply() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("complaintID", mView.getComplaintID())
                .put("reply", mView.getReply())
                .put("source", "2")
                .create();
        ComplainManageService.INSTANCE
                .sendComplainReply(baseMapReq)
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
                        mView.sendSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }

}
