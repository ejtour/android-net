package com.hll_sc_app.app.aftersales.entry;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesVerifyResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public interface IAfterSalesEntryContract {
    interface IAfterSalesEntryView extends ILoadView {
        void handleVerifyResult(AfterSalesVerifyResp resp);
    }

    interface IAfterSalesEntryPresenter extends IPresenter<IAfterSalesEntryView> {
        void verify(int refundBillType);
    }
}
