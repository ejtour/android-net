package com.hll_sc_app.app.order.place.confirm;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.place.OrderCommitReq;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */

public interface IPlaceOrderConfirmContract {
    interface IPlaceOrderConfirmView extends ILoadView {
        void commitSuccess(String masterBillIDs);
    }

    interface IPlaceOrderConfirmPresenter extends IPresenter<IPlaceOrderConfirmView> {
        void commitOrder(OrderCommitReq req);
    }
}
