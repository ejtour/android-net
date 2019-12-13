package com.hll_sc_app.app.report.refund.customerproduct.product;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.refund.RefundProductResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/13
 */

public interface IRefundProductContract {
    interface IRefundProductView extends IExportView {
        void setData(RefundProductResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IRefundProductPresenter extends IPresenter<IRefundProductView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
