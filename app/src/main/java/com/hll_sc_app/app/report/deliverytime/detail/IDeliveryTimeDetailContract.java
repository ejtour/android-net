package com.hll_sc_app.app.report.deliverytime.detail;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author chukun
 * @since 2019/8/15
 */

public interface IDeliveryTimeDetailContract {
    interface IDeliveryTimeDetailView extends IExportView {
        void setData(DeliveryTimeResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IDeliveryTimeDetailPresenter extends IPresenter<IDeliveryTimeDetailContract.IDeliveryTimeDetailView> {

        void refresh();

        void loadMore();

        void export(String email);
    }
}
