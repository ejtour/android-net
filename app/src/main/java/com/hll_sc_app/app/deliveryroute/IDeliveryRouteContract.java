package com.hll_sc_app.app.deliveryroute;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.other.RouteBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public interface IDeliveryRouteContract {
    interface IDeliveryRouteView extends ILoadView {
        void setRouteInfo(List<RouteBean> list, boolean append);
    }

    interface IDeliveryRoutePresenter extends IPresenter<IDeliveryRouteView> {
        void refresh();

        void loadMore();
    }
}
