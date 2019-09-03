package com.hll_sc_app.app.report.refund.refundcollect.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.RefundedReq;
import com.hll_sc_app.bean.report.refund.RefundedResp;
import com.hll_sc_app.bean.report.refund.WaitRefundCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;

/**
 * 退货明细
 * @author chukun
 * @date 2019/08/16
 */
public interface RefundedDetailContract {

    interface IRefundedDetailView extends ILoadView {
        /**
         * 展示退货明细
         * @param refundedResp
         */
        void showRefundedDetail(RefundedResp refundedResp, boolean append);

        /**
         * 获取请求参数
         * @return
         */
        RefundedReq getRequestParams();

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

    interface IWaitRefundCustomerDetailPresenter extends IPresenter<IRefundedDetailView> {
        /**
         * 查询退货明细
         * @param showLoading true-显示对话框
         */
        void queryRefundedDetail(boolean showLoading);

        void loadMoreRefundedDetail();

        /**
         * 导出
         * @param email 邮箱地址
         */
        void exportRefundedDetail(String email, String reqParams);
    }
}
