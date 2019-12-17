package com.hll_sc_app.app.report.deliverytime;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;

/**
 * 配送及时率
 *
 * @author chukun
 * @date 2019/08/16
 */
public interface IDeliveryTimeContract {

    interface IDeliveryTimeView extends ILoadView {
        void setData(DeliveryTimeResp resp);
    }

    interface IDeliveryTimePresenter extends IPresenter<IDeliveryTimeView> {
    }
}
