package com.hll_sc_app.app.aftersales.apply.presenter;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.rest.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesRejectPresenter extends BaseAfterSalesApplyPresenter {

    public AfterSalesRejectPresenter(AfterSalesApplyParam param) {
        super(param);
    }

    @Override
    public void submit() {
        OrderInspectionReq req = new OrderInspectionReq();
        req.setFlag(1);
        req.setRejectExplain(mParam.getExplain());
        req.setRejectReason(mParam.getReason());
        req.setRejectVoucher(mParam.getVoucher());
        req.setSubBillID(mParam.getSubBillID());
        List<OrderInspectionReq.OrderInspectionItem> items = new ArrayList<>();
        for (OrderDetailBean bean : mParam.getOrderDetailList()) {
            items.add(OrderInspectionReq.OrderInspectionItem.copyFromDetailList(bean));
        }
        req.setList(items);
        Order.inspectionOrder(req, new SimpleObserver<OrderInspectionResp>(mView) {
            @Override
            public void onSuccess(OrderInspectionResp orderInspectionResp) {
                mView.submitSuccess(null);
            }
        });
    }
}
