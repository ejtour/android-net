package com.hll_sc_app.app.report.refund.statistic.details;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.refund.RefundDetailsResp;
import com.hll_sc_app.impl.IExportView;

/**
 * 退货明细
 * @author chukun
 * @date 2019/08/16
 */
public interface IRefundDetailsContract {

    interface IRefundDetailsView extends IExportView {
        void setData(RefundDetailsResp refundDetailsResp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IRefundDetailsPresenter extends IPresenter<IRefundDetailsView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
