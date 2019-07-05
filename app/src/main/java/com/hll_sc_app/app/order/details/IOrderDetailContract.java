package com.hll_sc_app.app.order.details;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public interface IOrderDetailContract {
    interface IOrderDetailView extends ILoadView {
        void updateOrderData(OrderResp resp);

        void handleStatusChanged();

        /**
         * 绑定邮箱
         */
        void bindEmail();

        /**
         * 导出成功
         *
         * @param email 邮箱
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param msg 失败消息
         */
        void exportFailure(String msg);
    }

    interface IOrderDetailPresenter extends IPresenter<IOrderDetailView> {
        void orderCancel(String cancelReason);

        void orderReceive();

        void orderDeliver();

        void exportAssemblyOrder(String subBillID, String email);

        void exportDeliveryOrder(String subBillID, String email);
    }
}
