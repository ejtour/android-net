package com.hll_sc_app.app.order.details;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public interface IOrderDetailContract {
    interface IOrderDetailView extends IExportView {
        void updateOrderData(OrderResp resp);

        void handleStatusChanged();

        void updateOrderTraceLog(List<OrderTraceBean> list);

        void handleAfterSalesInfo(List<AfterSalesBean> list);

        void showExpressInfoDialog(List<ExpressResp.ExpressBean> beans);
    }

    interface IOrderDetailPresenter extends IPresenter<IOrderDetailView> {
        void orderRemark(String remark);

        void orderCancel(String cancelReason);

        void orderReceive();

        void orderDeliver();

        void exportAssemblyOrder(String subBillID, String email);

        void exportDeliveryOrder(String subBillID, String email);

        void getAfterSalesInfo();

        void getExpressCompanyList(String groupID, String shopID);

        void expressDeliver(String expressName, String expressNo);
    }
}
