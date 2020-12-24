package com.hll_sc_app.app.order.trace;

import com.amap.api.maps.model.LatLng;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/17/20.
 */
interface IOrderTraceContract {
    interface IOrderTraceView extends ILoadView {
        void setData(List<LatLng> list);
    }

    interface IOrderTracePresenter extends IPresenter<IOrderTraceView> {

    }
}
