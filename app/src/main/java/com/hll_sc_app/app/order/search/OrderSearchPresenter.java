package com.hll_sc_app.app.order.search;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.search.OrderSearchResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class OrderSearchPresenter implements IOrderSearchContract.IOrderSearchPresenter {
    private IOrderSearchContract.IOrderSearchView mView;

    private OrderSearchPresenter() {
    }

    public static OrderSearchPresenter newInstance() {
        return new OrderSearchPresenter();
    }

    @Override
    public void requestSearch(String searchWords) {
        Order.requestSearch(searchWords, new SimpleObserver<OrderSearchResp>(mView, false) {
            @Override
            public void onSuccess(OrderSearchResp resp) {
                mView.refreshSearchData(resp.getList());
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(IOrderSearchContract.IOrderSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
