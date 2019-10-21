package com.hll_sc_app.app.feedbackcomplain.feedback;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.FeedbackListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class FeedbackListPresent implements IFeedbackListContract.IPresent {
    private IFeedbackListContract.IView mView;

    private int pageSize = 20;
    private int pageTempNum = 1;
    private int pageNum = 1;

    public static FeedbackListPresent newInstance() {
        return new FeedbackListPresent();
    }

    @Override
    public void register(IFeedbackListContract.IView view) {
        mView = view;
    }


    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(pageTempNum))
                .put("pageSize", String.valueOf(pageSize))
                .create();
        ComplainManageService.INSTANCE
                .queryFeedbackList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<FeedbackListResp>() {
                    @Override
                    public void onSuccess(FeedbackListResp feedbackListResp) {
                        mView.querySuccess(feedbackListResp.getList(), pageTempNum > 1);
                        pageNum = pageTempNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageTempNum = pageNum;
                    }
                });
    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryList(false);
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryList(false);
    }
}
