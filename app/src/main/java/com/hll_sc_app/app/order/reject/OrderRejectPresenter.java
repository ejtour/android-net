package com.hll_sc_app.app.order.reject;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class OrderRejectPresenter implements IOrderRejectContract.IOrderRejectPresenter {
    private IOrderRejectContract.IOrderRejectView mView;


    private OrderRejectPresenter() {
    }

    public static OrderRejectPresenter newInstance() {
        return new OrderRejectPresenter();
    }

    @Override
    public void rejectOrder(int rejectReason, String rejectExplain, String rejectVoucher, OrderResp orderResp) {
        OrderInspectionReq req = new OrderInspectionReq();
        req.setFlag(1);
        req.setRejectExplain(rejectExplain);
        req.setRejectReason(String.valueOf(rejectReason));
        req.setRejectVoucher(rejectVoucher);
        req.setSubBillID(orderResp.getSubBillID());
        List<OrderInspectionReq.OrderInspectionItem> items = new ArrayList<>();
        for (OrderDetailBean bean : orderResp.getBillDetailList()) {
            OrderInspectionReq.OrderInspectionItem item = OrderInspectionReq.OrderInspectionItem.copyFromDetailList(bean);
            items.add(item);
        }
        req.setList(items);
        Order.inspectionOrder(req, new SimpleObserver<OrderInspectionResp>(mView) {
            @Override
            public void onSuccess(OrderInspectionResp orderInspectionResp) {
                mView.rejectSuccess();
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(IOrderRejectContract.IOrderRejectView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
