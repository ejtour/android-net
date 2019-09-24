package com.hll_sc_app.app.order.place.confirm;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.place.OrderCommitReq;
import com.hll_sc_app.bean.order.place.OrderCommitResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */

public class PlaceOrderConfirmPresenter implements IPlaceOrderConfirmContract.IPlaceOrderConfirmPresenter {
    private IPlaceOrderConfirmContract.IPlaceOrderConfirmView mView;

    private PlaceOrderConfirmPresenter() {
    }

    public static PlaceOrderConfirmPresenter newInstance() {
        return new PlaceOrderConfirmPresenter();
    }

    @Override
    public void register(IPlaceOrderConfirmContract.IPlaceOrderConfirmView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void commitOrder(OrderCommitReq req) {
        Order.commitOrder(req, new SimpleObserver<OrderCommitResp>(mView) {
            @Override
            public void onSuccess(OrderCommitResp resp) {
                mView.commitSuccess(resp.getMasterBillIDs());
            }
        });
    }
}
