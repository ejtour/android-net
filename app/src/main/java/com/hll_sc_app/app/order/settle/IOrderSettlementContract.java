package com.hll_sc_app.app.order.settle;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.settle.CashierResp;
import com.hll_sc_app.bean.order.settle.PayWayBean;
import com.hll_sc_app.bean.order.settle.PayWaysReq;
import com.hll_sc_app.bean.order.settle.SettlementResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public interface IOrderSettlementContract {
    interface IOrderSettlementView extends ILoadView {
        /**
         * finish
         */
        void closeActivity();

        /**
         * 展示支付方式列表
         *
         * @param list 支付方式列表
         */
        void showPayWays(List<PayWayBean> list);

        /**
         * 结算成功
         */
        void settleSuccess();

        /**
         * 显示收款二维码
         */
        void showQRCode(CashierResp resp);

        /**
         * 处理支付状态
         */
        void handlePayStatus(SettlementResp resp);
    }

    interface IOrderSettlementPresenter extends IPresenter<IOrderSettlementView> {
        /**
         * 查询可用支付方式列表
         *
         * @param payType 1.在线支付 2.货到付款
         */
        void getPayWays(int payType,List<PayWaysReq.GroupList> groupLists);


        /**
         * 验货支付
         *
         * @param paymentWay 付款方式
         */
        void inspectionPay(String paymentWay);

        /**
         * 查询支付结果
         */
        void queryPayResult(String payOrderNo);

        /**
         * 获取收银台
         *
         * @param payType 支付方式
         */
        void getCashier(String payType);
    }
}
