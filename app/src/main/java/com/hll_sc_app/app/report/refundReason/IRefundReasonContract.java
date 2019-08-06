package com.hll_sc_app.app.report.refundReason;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.RefundReasonStaticsResp;

public interface IRefundReasonContract {
    interface IView extends ILoadView {
        boolean isContainDeposit();

        String getFilterStartDate();

        String getFilterEndDate();

        void querySuccess(RefundReasonStaticsResp staticsResp);
    }

    interface IPresent extends IPresenter<IView> {
        void queryRefundReasonStatics();
    }
}
