package com.hll_sc_app.app.report.refund.wait.customer;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.WaitRefundCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;

/**
 * 待退客户明细
 * @author chukun
 * @date 2019/08/16
 */
public interface WaitRefundCustomerDetailContract {

    interface IWaitRefundCustomerDetailView extends ILoadView {
        /**
         * 展示退货客户明细
         * @param refundCustomerResp
         */
        void showRefundCustomerDetail(WaitRefundCustomerResp refundCustomerResp, boolean append);

        /**
         * 获取请求参数
         * @return
         */
        WaitRefundReq getRequestParams();

        /**
         * 导出成功
         *
         * @param email 邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param tip 失败提示
         */
        void exportFailure(String tip);

        /**
         * 绑定邮箱
         */
        void bindEmail();

        void export(String email);

    }

    interface IWaitRefundCustomerDetailPresenter extends IPresenter<IWaitRefundCustomerDetailView> {
        /**
         * 查询待退客户明细
         * @param showLoading true-显示对话框
         */
        void queryRefundCustomerDetail(boolean showLoading);

        void loadMoreRefundCustomerDetail();

        /**
         * 导出
         * @param email 邮箱地址
         */
        void exportRefundCustomerDetail(String email, String reqParams);
    }
}
