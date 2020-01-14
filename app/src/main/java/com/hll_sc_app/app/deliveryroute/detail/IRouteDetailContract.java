package com.hll_sc_app.app.deliveryroute.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.other.RouteDetailResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/28
 */

public interface IRouteDetailContract {
    interface IRouteDetailView extends ILoadView {
        void setRouteDetailData(RouteDetailResp resp, boolean append);

        String getDeliveryNo();

        String getShopID();

        String getDate();
    }

    interface IRouteDetailPresenter extends IPresenter<IRouteDetailView> {
        void refresh();

        void loadMore();
    }
}
