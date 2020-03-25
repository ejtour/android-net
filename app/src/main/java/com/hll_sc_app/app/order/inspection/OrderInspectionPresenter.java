package com.hll_sc_app.app.order.inspection;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.rest.User;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class OrderInspectionPresenter implements IOrderInspectionContract.IOrderInspectionPresenter {
    private IOrderInspectionContract.IOrderInspectionView mView;

    public static OrderInspectionPresenter newInstance() {
        return new OrderInspectionPresenter();
    }

    @Override
    public void start() {
        User.queryGroupParam("15", new SimpleObserver<List<GroupParamBean>>(mView, false) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                Integer result = null;
                if (!CommonUtils.isEmpty(groupParamBeans))
                    result = groupParamBeans.get(0).getParameValue();
                mView.gotInspectionMode(result);
            }
        });
    }

    @Override
    public void register(IOrderInspectionContract.IOrderInspectionView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void confirmOrder(OrderInspectionReq req) {
        Order.inspectionOrder(req, new SimpleObserver<OrderInspectionResp>(mView) {
            @Override
            public void onSuccess(OrderInspectionResp orderInspectionResp) {
                mView.confirmSuccess(orderInspectionResp);
            }
        });
    }
}
