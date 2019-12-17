package com.hll_sc_app.app.report.refund;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.RefundResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public interface IRefundContract {

    interface IRefundCustomerProductView extends ILoadView {
        void setData(RefundResp resp);

        int getFlag();
    }

    interface IRefundCustomerProductPresenter extends IPresenter<IRefundCustomerProductView> {
    }
}
