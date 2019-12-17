package com.hll_sc_app.app.report.refund;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.refund.RefundResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public class RefundPresenter implements IRefundContract.IRefundCustomerProductPresenter {
    private IRefundContract.IRefundCustomerProductView mView;

    static RefundPresenter newInstance() {
        return new RefundPresenter();
    }

    @Override
    public void start() {
        Report.queryRefundInfo(mView.getFlag(), new SimpleObserver<RefundResp>(mView) {
            @Override
            public void onSuccess(RefundResp refundResp) {
                mView.setData(refundResp);
            }
        });
    }

    @Override
    public void register(IRefundContract.IRefundCustomerProductView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
