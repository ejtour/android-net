package com.hll_sc_app.app.order;

import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.impl.IExportView;

import java.util.List;


/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */
interface IOrderManageContract {

    interface IOrderManageView extends IExportView {
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
        boolean setDeliverType(String type);

        /**
         * 刷新列表数据
         */
        void updateListData(List<OrderResp> resps, boolean append);

        /**
         * 状态改变
         */
        void statusChanged();

        /**
         * 更新待发货头部信息
         *
         * @return 处理是否完成
         */
        boolean updateDeliverHeader(List<DeliverNumResp.DeliverType> deliverTypes);

        /**
         * 展示物流公司列表
         */
        void showExpressInfoDialog(List<ExpressResp.ExpressBean> beans);
    }

    interface IOrderManagePresenter extends IPresenter<IOrderManageView> {

        void refresh();

        void loadMore();

        void getOrderDetails(String subBillId);

        void receiveOrder(String subBillIds);

        void deliver(String subBillIds, String expressName, String expressNo);

        void exportAssemblyOrder(List<String> subBillIds, String email, String source);

        void exportDeliveryOrder(List<String> subBillIds, String email, String source);

        void exportSpecialOrder(int type, String email, String source);

        void exportNormalOrder(int type, String email, List<String> billNoList, String source);

        void getExpressCompanyList(String groupID, String shopID, String source);
    }
}
