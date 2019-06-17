package com.hll_sc_app.app.order.reject;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public interface IOrderRejectContract {
    interface IOrderRejectView extends ILoadView {
        /**
         * 拒收成功
         */
        void rejectSuccess();
    }

    interface IOrderRejectPresenter extends IPresenter<IOrderRejectView> {

        /**
         * 订单拒收
         *
         * @param rejectReason  拒收原因
         * @param rejectExplain 拒收说明
         * @param rejectVoucher 拒收凭证
         * @param orderResp     订单详情
         */
        void rejectOrder(int rejectReason, String rejectExplain, String rejectVoucher, OrderResp orderResp);
    }
}
