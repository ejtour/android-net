package com.hll_sc_app.app.report.refund.wait.product;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.WaitRefundProductResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;

/**
 * 待退商品明细
 * @author chukun
 * @date 2019/08/16
 */
public interface WaitRefundProductDetailContract {

    interface IWaitRefundProductDetailView extends ILoadView {
        /**
         * 展示退货商品明细
         * @param refundProductResp
         */
        void showRefundProductDetail(WaitRefundProductResp refundProductResp, boolean append);

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

    interface IWaitRefundProductDetailPresenter extends IPresenter<IWaitRefundProductDetailView> {
        /**
         * 查询待退商品明细
         * @param showLoading true-显示对话框
         */
        void queryRefundProductDetail(boolean showLoading);

        void loadMoreRefundProductDetail();

        /**
         * 导出
         * @param email 邮箱地址
         */
        void exportRefundProductDetail(String email, String reqParams);
    }
}
