package com.hll_sc_app.app.search.presenter;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.bean.order.search.OrderSearchResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class OrderSearchPresenter extends BaseSearchPresenter {

    @Override
    public void requestSearch(String searchWords) {
        Order.requestSearch(searchWords, new SimpleObserver<OrderSearchResp>(mView, false) {
            @Override
            public void onSuccess(OrderSearchResp resp) {
                List<NameValue> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(resp.getList())) {
                    for (OrderSearchBean bean : resp.getList()) {
                        list.add(new NameValue(bean.getName(), bean.getShopMallId()));
                    }
                }
                mView.refreshSearchData(list);
            }
        });
    }
}
