package com.hll_sc_app.app.order.transfer;

import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.transfer.TransferBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public interface IOrderTransferContract {
    interface IOrderTransferView extends ILoadView {
        /**
         * @return 订单参数
         */
        OrderParam getOrderParam();

        /**
         * @return 订单状态
         */
        OrderType getOrderStatus();

        /**
         * 刷新列表数据
         */
        void setListData(List<TransferBean> beans, boolean append);

        /**
         * 商城下单成功
         */
        void mallOrderSuccess();

        /**
         * 更新待转单数
         */
        void updatePendingTransferNum(int pendingTransferNum);
    }

    interface IOrderTransferPresenter extends IPresenter<IOrderTransferView> {
        /**
         * 下拉刷新
         */
        void refresh();

        /**
         * 上拉加载
         */
        void loadMore();

        /**
         * 获取转单详情
         */
        void getTransferOrderDetail(String subBillId);

        /**
         * 商城下单
         */
        void mallOrder(List<String> ids);
    }
}
