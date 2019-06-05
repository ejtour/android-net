package com.hll_sc_app.app.order;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;

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
        int getOrderStatus();

        /**
         * @return 发货类型
         */
        String getDeliverType();

        /**
         * 刷新列表数据
         */
        void refreshListData(List<OrderResp> resps);

        /**
         * 追加列表数据
         */
        void appendListData(List<OrderResp> resps);
    }

    interface IOrderManagePresenter extends IPresenter<IOrderManageView> {
        void refresh();

        void loadMore();
    }
}
