package com.hll_sc_app.app.order.statistic;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.order.statistic.OrderStatisticResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

interface IOrderStatisticContract {
    interface IOrderStatisticView extends ILoadView {
        void setData(OrderStatisticResp resp);

        BaseMapReq.Builder getReq();
    }

    interface IOrderStatisticPresenter extends IPresenter<IOrderStatisticView> {
    }
}
