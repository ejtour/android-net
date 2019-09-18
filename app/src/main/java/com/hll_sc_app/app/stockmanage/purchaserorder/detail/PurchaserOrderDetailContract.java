package com.hll_sc_app.app.stockmanage.purchaserorder.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderResp;

/**
 * 采购单详情查询
 * @author chukun
 * @date 2019/9/10
 */
public interface PurchaserOrderDetailContract {

    interface IPurchaserOrderDetailView extends ILoadView {
        /**
         * 展示采购单详情列表
         * @param purchaserOrderResp
         */
        void showPurchaserOrderDetail(PurchaserOrderDetailResp purchaserOrderResp);

        String getPurchaserBillID();
    }

    interface IPurchaserOrderDetailPresenter extends IPresenter<IPurchaserOrderDetailView> {
        /**
         * 加载采购单列表
         * @param showLoading true-显示对话框
         */
        void queryPurchaserOrderDetail(boolean showLoading);

    }
}
