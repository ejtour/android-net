package com.hll_sc_app.app.report.refund.reason;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.refund.RefundReasonBean;
import com.hll_sc_app.rest.Report;

public class RefundReasonPresenter implements IRefundReasonContract.IPresent {

    private IRefundReasonContract.IView mView;

    public static RefundReasonPresenter newInstance() {
        return new RefundReasonPresenter();
    }

    @Override
    public void register(IRefundReasonContract.IView view) {
        mView = view;
    }

    @Override
    public void start() {
        load(true);
    }

    private void load(boolean showLoading) {
        Report.queryRefundReasonStatistic(mView.getReq().create(), new SimpleObserver<SingleListResp<RefundReasonBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<RefundReasonBean> refundReasonBeanSingleListResp) {
                mView.setData(refundReasonBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void refresh() {
        load(false);
    }
}
