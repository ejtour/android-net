package com.hll_sc_app.app.stockmanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stock.purchaserorder.PurchaserOrderResp;

/**
 * 采购单查询
 *
 * @author chukun
 * @date 2019/9/10
 */
public interface PurchaserOrderContract {

    interface IPurchaserOrderView extends ILoadView {
        /**
         * 展示采购单列表
         * @param purchaserOrderResp
         */
        void showPurchaserOrderList(PurchaserOrderResp purchaserOrderResp, boolean append, int total);


        /**
         *
         * 获取请求参数
         * @return
         */
        PurchaserOrderReq getRequestParams();
    }

    interface IPurchaserOrderPresenter extends IPresenter<IPurchaserOrderView> {
        /**
         * 加载采购单列表
         * @param showLoading true-显示对话框
         */
        void queryPurchaserOrderList(boolean showLoading);

        /**
         * 加载更多采购单列表
         */
        void queryMorePurchaserOrderList();

    }
}
