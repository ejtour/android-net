package com.hll_sc_app.app.report.refund.customerproduct.customer;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.refund.RefundCustomerResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/13
 */

public interface IRefundCustomerContract {
    interface IRefundCustomerView extends IExportView {
        void setData(RefundCustomerResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IRefundCustomerPresenter extends IPresenter<IRefundCustomerView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
