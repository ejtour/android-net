package com.hll_sc_app.app.order.statistic.shop;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.order.statistic.OrderStatisticShopBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/8
 */

class ShopOrderStatisticPresenter implements IShopOrderStatisticContract.IShopOrderStatisticPresenter {
    private IShopOrderStatisticContract.IShopOrderStatisticView mView;

    public static ShopOrderStatisticPresenter newInstance() {
        return new ShopOrderStatisticPresenter();
    }

    private ShopOrderStatisticPresenter() {
    }

    @Override
    public void start() {
        Order.queryOrderShopStatistic(mView.getReq()
                .put("date", CalendarUtils.toLocalDate(new Date()))
                .create(), new SimpleObserver<SingleListResp<OrderStatisticShopBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<OrderStatisticShopBean> orderStatisticShopBeanSingleListResp) {
                mView.setData(orderStatisticShopBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void register(IShopOrderStatisticContract.IShopOrderStatisticView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
