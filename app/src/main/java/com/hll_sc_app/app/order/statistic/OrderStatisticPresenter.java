package com.hll_sc_app.app.order.statistic;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.statistic.OrderStatisticResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

class OrderStatisticPresenter implements IOrderStatisticContract.IOrderStatisticPresenter {
    private IOrderStatisticContract.IOrderStatisticView mView;

    public static OrderStatisticPresenter newInstance() {
        return new OrderStatisticPresenter();
    }

    private OrderStatisticPresenter() {
    }

    @Override
    public void start() {
        Order.queryOrderStatistic(
                mView.getReq().put("date", CalendarUtils.toLocalDate(new Date())),
                new SimpleObserver<OrderStatisticResp>(mView, false) {
                    @Override
                    public void onSuccess(OrderStatisticResp orderStatisticResp) {
                        mView.setData(orderStatisticResp);
                    }
                });
    }

    @Override
    public void register(IOrderStatisticContract.IOrderStatisticView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
