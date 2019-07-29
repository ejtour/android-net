package com.hll_sc_app.app.report.refundReason;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.report.RefundReasonStaticsResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class RefundReasonPresenter implements IRefundReasonContract.IPresent {

    private IRefundReasonContract.IView mView;

    public static RefundReasonPresenter newInstance() {
        return new RefundReasonPresenter();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(IRefundReasonContract.IView view) {
        mView = view;
    }

    @Override
    public void queryRefundReasonStatics() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }

        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .put("sign", mView.isContainDeposit() ? "1" : "2")
                .put("startDate", mView.getFilterStartDate())
                .put("endDate", mView.getFilterEndDate()).create();

        ReportService.INSTANCE
                .queryRefundReasonStatics(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<RefundReasonStaticsResp>() {
                    @Override
                    public void onSuccess(RefundReasonStaticsResp staticsResp) {
                        mView.querySuccess(staticsResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

}
