package com.hll_sc_app.app.aftersales.negotiationhistory;


import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;

public interface INegotiationHistoryContract {
    interface INegotiationHistoryView extends ILoadView {
        /**
         * 展示历史列表
         */
        void showHistoryList(NegotiationHistoryResp result);
    }

    interface INegotiationHistoryPresenter extends IPresenter<INegotiationHistoryView> {
        /**
         * 获取协商历史
         *
         * @param refundBillID 退换货订单id
         * @param subBillID    订单id
         */
        void getHistoryList(String refundBillID, String subBillID);
    }
}
