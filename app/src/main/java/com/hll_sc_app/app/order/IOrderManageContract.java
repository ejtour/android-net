package com.hll_sc_app.app.order;

import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;

import java.util.List;


/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */
interface IOrderManageContract {

    interface IOrderManageView extends ILoadView {
        /**
         * @return 订单参数
         */
        OrderParam getOrderParam();

        /**
         * @return 订单状态
         */
        OrderType getOrderStatus();

        /**
         * @return 发货类型
         */
        String getDeliverType();

        /**
         * 更新发货类型
         */
        void setDeliverType(String type);

        /**
         * 刷新列表数据
         */
        void refreshListData(List<OrderResp> resps);

        /**
         * 追加列表数据
         */
        void appendListData(List<OrderResp> resps);

        /**
         * 状态改变
         */
        void statusChanged();

        /**
         * 更新待发货头部信息
         */
        void updateDeliverHeader(List<DeliverNumResp.DeliverType> deliverTypes);
    }

    interface IOrderManagePresenter extends IPresenter<IOrderManageView> {
        void refreshList();

        void refresh();

        void loadMore();

        void receiveOrder(String subBillIds);

        void deliver(String subBillIds, String expressName, String expressNo);
    }
}
