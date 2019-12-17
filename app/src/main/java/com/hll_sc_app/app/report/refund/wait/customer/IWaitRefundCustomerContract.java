package com.hll_sc_app.app.report.refund.wait.customer;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.refund.RefundCustomerResp;
import com.hll_sc_app.impl.IExportView;

/**
 * 待退客户明细
 * @author chukun
 * @date 2019/08/16
 */
public interface IWaitRefundCustomerContract {

    interface IWaitRefundCustomerView extends IExportView {
        void setData(RefundCustomerResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IWaitRefundCustomerPresenter extends IPresenter<IWaitRefundCustomerView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
