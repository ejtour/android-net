package com.hll_sc_app.app.crm.customer.seas.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

public interface ICustomerSeasDetailContract {
    interface ICustomerSeasDetailView extends ILoadView {
        void success();
    }

    interface ICustomerSeasDetailPresenter extends IPresenter<ICustomerSeasDetailView> {
        void receive(String id, String purchaserID);
    }
}
