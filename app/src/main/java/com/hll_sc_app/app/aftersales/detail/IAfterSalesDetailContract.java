package com.hll_sc_app.app.aftersales.detail;

import com.hll_sc_app.app.aftersales.common.IAction;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;

public interface IAfterSalesDetailContract {
    interface IAfterSalesDetailView extends ILoadView, IAction {

        /**
         * 处理订单状态改变
         */
        void handleStatusChange();

        /**
         * 获取售后详情成功
         */
        void showDetail(AfterSalesBean data);
    }

    interface IAfterSalesDetailPresenter extends IPresenter<IAfterSalesDetailView> {
        /**
         * 获取售后详情
         *
         * @param refundBillID 售后订单 ID
         */
        void getDetail(String refundBillID);

        /**
         * 进行售后单操作
         */
        void doAction(int actionType, String payType, String billID, int status, int type, String msg);

        /**
         * 修改价格
         */
        void modifyPrice(String price, String refundBillDetailID, String refundBillID);
    }
}
