package com.hll_sc_app.app.order.details;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public interface IOrderDetailContract {
    interface IOrderDetailView extends ILoadView {
        void updateOrderData(OrderResp resp);
    }

    interface IOrderDetailPresenter extends IPresenter<IOrderDetailView> {
    }
}
