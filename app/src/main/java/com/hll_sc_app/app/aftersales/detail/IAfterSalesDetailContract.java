package com.hll_sc_app.app.aftersales.detail;

import com.hll_sc_app.app.aftersales.common.IAction;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesActionResp;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;

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

        /**
         *生成投诉单成功
         */
        void  genereteComplainSuccess(GenerateCompainResp resp);

        /**
         * 处理成功
         */
        void handleSuccess(AfterSalesActionResp resp);
    }

    interface IAfterSalesDetailPresenter extends IPresenter<IAfterSalesDetailView> {

        /**
         * 进行售后单操作
         */
        void doAction(int actionType, String payType, String msg);

        /**
         * 修改价格
         */
        void modifyPrice(String price, String refundBillDetailID);


        /**
         * 生成投诉单
         */
        void generateComplain(AfterSalesBean data);
    }
}
