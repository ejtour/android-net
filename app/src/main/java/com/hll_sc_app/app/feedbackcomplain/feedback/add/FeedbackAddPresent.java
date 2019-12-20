package com.hll_sc_app.app.feedbackcomplain.feedback.add;

import android.text.TextUtils;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class FeedbackAddPresent implements IFeedbackAddContract.IPresent {
    private IFeedbackAddContract.IView mView;


    public static FeedbackAddPresent newInstance() {
        return new FeedbackAddPresent();
    }

    @Override
    public void register(IFeedbackAddContract.IView view) {
        mView = view;
    }


    @Override
    public void addFeedback(String content, List<String> imgs) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("content", content)
                .put("feedbackID", mView.getFeedbackID())
                .put("parentID", mView.getParentID())
                .put("contentImg", TextUtils.join(",", imgs))
                .create();

        ComplainManageService.INSTANCE
                .addFeedback(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        mView.addSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
