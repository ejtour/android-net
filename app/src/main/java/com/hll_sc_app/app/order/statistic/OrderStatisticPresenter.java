package com.hll_sc_app.app.order.statistic;

import com.hll_sc_app.base.bean.BaseMapReq;
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
    private int mPageNum;

    public static OrderStatisticPresenter newInstance() {
        return new OrderStatisticPresenter();
    }

    private OrderStatisticPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IOrderStatisticContract.IOrderStatisticView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    private void load(boolean showLoading) {
        boolean notOrder = mView.isNotOrder();
        BaseMapReq.Builder builder = mView.getReq().put("date", CalendarUtils.toLocalDate(new Date()));
        if (notOrder) {
            builder.put("pageSize", "10");
            builder.put("pageNum", String.valueOf(mPageNum));
        }
        Order.queryOrderStatistic(
                builder.create(), notOrder,
                new SimpleObserver<OrderStatisticResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(OrderStatisticResp orderStatisticResp) {
                        mView.setData(orderStatisticResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(orderStatisticResp.getList())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void loadMore() {
        load(false);
    }
}
