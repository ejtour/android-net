package com.hll_sc_app.app.order.statistic.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.order.statistic.OrderStatisticShopBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/8
 */

interface IShopOrderStatisticContract {
    interface IShopOrderStatisticView extends ILoadView {
        void setData(List<OrderStatisticShopBean> list);

        BaseMapReq.Builder getReq();
    }

    interface IShopOrderStatisticPresenter extends IPresenter<IShopOrderStatisticView> {
    }
}
