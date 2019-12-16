package com.hll_sc_app.app.report.refund.reason;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.refund.RefundReasonBean;

import java.util.List;

public interface IRefundReasonContract {
    interface IView extends ILoadView {
        BaseMapReq.Builder getReq();

        void setData(List<RefundReasonBean> list);
    }

    interface IPresent extends IPresenter<IView> {
        void refresh();
    }
}
