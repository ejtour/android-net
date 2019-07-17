package com.hll_sc_app.app.order.details;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

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
                0, null, null, null, getObserver(null));
    }

    @Override
    public void orderDeliver() {
        Order.modifyOrderStatus(2, mSubBillID,
                0, null, null, null, getObserver(null));
    }

    @Override
    public void exportAssemblyOrder(String subBillID, String email) {
        Order.exportAssembly(Collections.singletonList(subBillID), email, getExportObserver());
    }

    @Override
    public void exportDeliveryOrder(String subBillID, String email) {
        Order.exportDelivery(Collections.singletonList(subBillID), email, getExportObserver());
    }

    private SimpleObserver<ExportResp> getExportObserver() {
        return new SimpleObserver<ExportResp>(mView) {
            @Override
            public void onSuccess(ExportResp exportResp) {
                if (!TextUtils.isEmpty(exportResp.getEmail()))
                    mView.exportSuccess(exportResp.getEmail());
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }

            @Override
            public void onFailure(UseCaseException e) {
                if ("00120112037".equals(e.getCode())) mView.bindEmail();
                else if ("00120112038".equals(e.getCode())) mView.exportFailure("当前没有可导出的数据");
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }
        };
    }
}
