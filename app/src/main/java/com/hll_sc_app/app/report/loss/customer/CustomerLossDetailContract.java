package com.hll_sc_app.app.report.loss.customer;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossReq;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossResp;

/**
 * 流失客户明细
 * @author chukun
 * @date 2019/08/16
 */
public interface CustomerLossDetailContract {

    interface ICustomerLossDetailView extends ILoadView {
        /**
         * 展示流失客户明细
         * @param lossResp
         */
        void showCustomerLossDetail(CustomerAndShopLossResp lossResp, boolean append);

        /**
         * 获取请求参数
         * @return
         */
        CustomerAndShopLossReq getRequestParams();

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

    interface ICustomerLossDetailPresenter extends IPresenter<ICustomerLossDetailView> {
        /**
         * 查询客户流失明细
         * @param showLoading true-显示对话框
         */
        void queryCustomerLossDetail(boolean showLoading);

        void loadMoreCustomerLossDetail();

        /**
         * 导出
         * @param email 邮箱地址
         */
        void exportCustomerLossDetail(String email, String reqParams);
    }
}
