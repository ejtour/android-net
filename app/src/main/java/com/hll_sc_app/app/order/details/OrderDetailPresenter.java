package com.hll_sc_app.app.order.details;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;

import java.util.Collections;

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
        Order.queryOrderLog(mSubBillID, new SimpleObserver<SingleListResp<OrderTraceBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<OrderTraceBean> orderTraceBeanSingleListResp) {
                mView.updateOrderTraceLog(orderTraceBeanSingleListResp.getRecords());
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
                null, null, getObserver("成功放弃订单"));
    }

    private SimpleObserver<Object> getObserver(String msg) {
        return new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                if (!TextUtils.isEmpty(msg)) mView.showToast(msg);
                mView.handleStatusChanged();
            }
        };
    }

    @Override
    public void orderReceive() {
        Order.modifyOrderStatus(1, mSubBillID,
                0, null, null, null, getObserver("成功接单"));
    }

    @Override
    public void orderDeliver() {
        Order.modifyOrderStatus(2, mSubBillID,
                0, null, null, null, getObserver("成功发货"));
    }

    @Override
    public void exportAssemblyOrder(String subBillID, String email) {
        Order.exportAssembly(Collections.singletonList(subBillID), email, Utils.getExportObserver(mView));
    }

    @Override
    public void exportDeliveryOrder(String subBillID, String email) {
        Order.exportDelivery(Collections.singletonList(subBillID), email, Utils.getExportObserver(mView));
    }

    @Override
    public void getAfterSalesInfo() {
        Order.queryAssociatedAfterSalesOrder(1, mSubBillID, new SimpleObserver<SingleListResp<AfterSalesBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<AfterSalesBean> afterSalesBeanSingleListResp) {
                mView.handleAfterSalesInfo(afterSalesBeanSingleListResp.getRecords());
            }
        });
    }
}
