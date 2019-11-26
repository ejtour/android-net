package com.hll_sc_app.app.crm.customer.intent;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.customer.CustomerBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public interface ICustomerIntentContract {
    interface ICustomerIntentView extends ILoadView {
        void setData(List<CustomerBean> list, boolean append);

        int getType();

        String getSearchWords();
    }

    interface ICustomerIntentPresenter extends IPresenter<ICustomerIntentView> {
        void refresh();

        void loadMore();
    }
}
