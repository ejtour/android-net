package com.hll_sc_app.app.feedbackcomplain.feedback.detail;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class FeedbackDetailPresent implements IFeedbackDetailContract.IPresent {
    private IFeedbackDetailContract.IView mView;


    public static FeedbackDetailPresent newInstance() {
        return new FeedbackDetailPresent();
    }

    @Override
    public void register(IFeedbackDetailContract.IView view) {
        mView = view;
    }


    @Override
    public void queryFeedback(String id) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("feedbackID", id)
                .create();
        ComplainManageService.INSTANCE
                .queryFeedbackDetail(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<FeedbackDetailResp>() {
                    @Override
                    public void onSuccess(FeedbackDetailResp feedbackDetailResp) {
                        mView.showDetail(feedbackDetailResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


}
