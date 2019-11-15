package com.hll_sc_app.app.crm.customer;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.CrmCustomerResp;
import com.hll_sc_app.bean.customer.CrmShopResp;
import com.hll_sc_app.bean.home.VisitResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/15
 */

public interface ICrmCustomerContract {
    interface ICrmCustomerView extends ILoadView {
        void setShopInfo(CrmShopResp resp);

        void setCustomerInfo(CrmCustomerResp resp);

        void setVisitInfo(VisitResp resp);
    }

    interface ICrmCustomerPresenter extends IPresenter<ICrmCustomerView> {

    }
}
