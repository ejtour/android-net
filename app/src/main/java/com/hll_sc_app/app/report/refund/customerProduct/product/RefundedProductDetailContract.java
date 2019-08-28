package com.hll_sc_app.app.report.refund.customerProduct.product;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.RefundedCustomerReq;
import com.hll_sc_app.bean.report.refund.RefundedCustomerResp;
import com.hll_sc_app.bean.report.refund.RefundedProductReq;
import com.hll_sc_app.bean.report.refund.RefundedProductResp;

/**
 * 退货客户明细
 * @author chukun
 * @date 2019/08/16
 */
public interface RefundedProductDetailContract {

    interface IRefundedProductDetailView extends ILoadView {
        /**
         * 展示退货商品明细
         * @param refundedProductResp
         */
        void showRefundedProductDetail(RefundedProductResp refundedProductResp, boolean append);

        /**
         * 获取请求参数
         * @return
         */
        RefundedProductReq getRequestParams();

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

    interface IRefundedProductDetailPresenter extends IPresenter<IRefundedProductDetailView> {
        /**
         * 退货商品明细
         * @param showLoading true-显示对话框
         */
        void queryRefundedProductDetail(boolean showLoading);

        void loadMoreRefundedProductDetail();

        /**
         * 导出
         * @param email 邮箱地址
         */
        void exportRefundedProductDetail(String email, String reqParams);
    }
}
