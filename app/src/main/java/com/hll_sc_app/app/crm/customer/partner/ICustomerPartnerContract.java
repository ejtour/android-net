package com.hll_sc_app.app.crm.customer.partner;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public interface ICustomerPartnerContract {
    interface ICustomerPartnerView extends ILoadView {
        void setData(CooperationPurchaserResp resp, boolean append);

        boolean isAll();

        String getSearchWords();
    }

    interface ICustomerPartnerPresenter extends IPresenter<ICustomerPartnerView> {
        void refresh();

        void loadMore();
    }
}
