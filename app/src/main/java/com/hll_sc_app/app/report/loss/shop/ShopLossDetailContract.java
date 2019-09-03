package com.hll_sc_app.app.report.loss.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossReq;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossResp;

/**
 * 流失门店明细
 * @author chukun
 * @date 2019/08/16
 */
public interface ShopLossDetailContract {

    interface IShopLossDetailView extends ILoadView {
        /**
         * 展示流失门店明细
         * @param lossResp
         */
        void showShopLossDetail(CustomerAndShopLossResp lossResp, boolean append);

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

    interface IShopLossDetailPresenter extends IPresenter<IShopLossDetailView> {
        /**
         * 查询流失门店明细
         * @param showLoading true-显示对话框
         */
        void queryShopLossDetail(boolean showLoading);

        void loadMoreShopLossDetail();

        /**
         * 导出
         * @param email 邮箱地址
         */
        void exportShopLossDetail(String email, String reqParams);
    }
}
