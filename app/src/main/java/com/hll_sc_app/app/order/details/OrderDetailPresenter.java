package com.hll_sc_app.app.order.details;

import android.support.annotation.NonNull;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public class OrderDetailPresenter implements IOrderDetailContract.IOrderDetailPresenter {
    private String mSubBillID;
    private IOrderDetailContract.IOrderDetailView mView;

    private OrderDetailPresenter(String subBillID) {
        mSubBillID = subBillID;
    }

    public static OrderDetailPresenter newInstance(@NonNull String subBillID) {
        return new OrderDetailPresenter(subBillID);
    }

    @Override
    public void start() {
        Order.getOrderDetails(mSubBillID, new SimpleObserver<OrderResp>(mView) {
            @Override
            public void onSuccess(OrderResp resp) {
                mView.updateOrderData(resp);
            }
        });
    }

    @Override
    public void register(IOrderDetailContract.IOrderDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void orderCancel(String cancelReason) {
        Order.modifyOrderStatus(3, mSubBillID, 2, cancelReason,
                null, null, getObserver());
    }

    private SimpleObserver<Object> getObserver() {
        return new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.handleStatusChanged();
            }
        };
    }

    @Override
    public void orderReceive() {
        Order.modifyOrderStatus(1, mSubBillID,
                0, null, null, null, getObserver());
    }

    @Override
    public void orderDeliver() {
        Order.modifyOrderStatus(2, mSubBillID,
                0, null, null, null, getObserver());
    }
}
