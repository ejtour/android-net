package com.hll_sc_app.app.order.transfer.details;

import androidx.annotation.NonNull;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.transfer.OrderResultResp;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.Collections;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class TransferDetailPresenter implements ITransferDetailContract.ITransferDetailPresenter {
    private String mID;
    private ITransferDetailContract.ITransferDetailView mView;

    @Override
    public void mallOrder() {
        Order.mallOrder(Collections.singletonList(mID), new SimpleObserver<OrderResultResp>(mView) {
            @Override
            public void onSuccess(OrderResultResp resp) {
                if (CommonUtils.isEmpty(resp.getRecords()) && CommonUtils.isEmpty(resp.getShelfFlowRecords())) {
                    mView.handleStatusChanged();
                } else {
                    mView.inventoryShortage(resp);
                }
            }
        });
    }

    private TransferDetailPresenter(String id) {
        mID = id;
    }

    public static TransferDetailPresenter newInstance(@NonNull String subBillID) {
        return new TransferDetailPresenter(subBillID);
    }

    @Override
    public void start() {
        Order.getTransferDetail(mID, new SimpleObserver<TransferBean>(mView) {
            @Override
            public void onSuccess(TransferBean bean) {
                mView.updateOrderData(bean);
            }
        });
    }

    @Override
    public void register(ITransferDetailContract.ITransferDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void orderCancel(String cancelReason, int billSource) {
        UserBean user = GreenDaoUtils.getUser();
        Order.cancelTransfer(user.getEmployeeName(), mID, billSource, cancelReason, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("取消下单成功");
                mView.handleStatusChanged();
            }
        });
    }
}
