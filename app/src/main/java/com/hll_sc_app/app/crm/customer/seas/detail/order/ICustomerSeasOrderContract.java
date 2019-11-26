package com.hll_sc_app.app.crm.customer.seas.detail.order;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public interface ICustomerSeasOrderContract {
    interface ICustomerSeasOrderView extends ILoadView {
        void setData(List<OrderResp> list, boolean append);

        String getShopID();
    }

    interface ICustomerSeasOrderPresenter extends IPresenter<ICustomerSeasOrderView> {
        void refresh();

        void loadMore();
    }
}
