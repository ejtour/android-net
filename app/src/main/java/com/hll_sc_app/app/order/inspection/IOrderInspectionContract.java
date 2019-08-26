package com.hll_sc_app.app.order.inspection;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public interface IOrderInspectionContract {
    interface IOrderInspectionView extends ILoadView {
        /**
         * 验货成功
         */
        void confirmSuccess(OrderInspectionResp result);

        void gotInspectionMode(Integer result);
    }

    interface IOrderInspectionPresenter extends IPresenter<IOrderInspectionView> {

        /**
         * 订单验货
         */
        void confirmOrder(OrderInspectionReq req);
    }
}
