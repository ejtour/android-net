package com.hll_sc_app.app.report.customersettle.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleDetailResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

interface ICustomerSettleDetailContract {
    interface ICustomerSettleDetailView extends ILoadView {
        BaseMapReq.Builder getReq();

        void setData(CustomerSettleDetailResp resp);
    }

    interface ICustomerSettleDetailPresenter extends IPresenter<ICustomerSettleDetailView> {
    }
}
