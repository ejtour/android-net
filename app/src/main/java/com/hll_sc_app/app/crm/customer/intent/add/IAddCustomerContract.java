package com.hll_sc_app.app.crm.customer.intent.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.CustomerBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public interface IAddCustomerContract {
    interface IAddCustomerView extends ILoadView {
        void saveSuccess();
    }

    interface IAddCustomerPresenter extends IPresenter<IAddCustomerView> {
        void save(CustomerBean bean);
    }
}
