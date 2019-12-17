package com.hll_sc_app.app.report.refund.wait.product;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.refund.RefundProductResp;
import com.hll_sc_app.impl.IExportView;

/**
 * 待退商品明细
 * @author chukun
 * @date 2019/08/16
 */
public interface WaitRefundProductContract {

    interface IWaitRefundProductView extends IExportView {
        void setData(RefundProductResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IWaitRefundProductPresenter extends IPresenter<IWaitRefundProductView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
