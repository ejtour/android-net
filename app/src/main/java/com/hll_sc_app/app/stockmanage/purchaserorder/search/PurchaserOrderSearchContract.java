package com.hll_sc_app.app.stockmanage.purchaserorder.search;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchResp;

import java.util.List;

/**
 * 采购单搜索
 * @author chukun
 * @date 2019/9/10
 */
public interface PurchaserOrderSearchContract {

    interface IPurchaserOrderSearchView extends ILoadView {
        /**
         * 展示采购单搜索列表
         * @param purchaserOrderSearchResp
         */
        void showPurchaserOrderSearchList(PurchaserOrderSearchResp purchaserOrderSearchResp,boolean append);

        List<String> getSelectiveSuppliers();

        String getSearchKey();
    }

    interface IPurchaserOrderSearchPresenter extends IPresenter<IPurchaserOrderSearchView> {
        /**
         * 加载采购单搜索列表
         * @param showLoading true-显示对话框
         */
        void queryPurchaserOrderSearchList(boolean showLoading);

        void queryMorePurchaserOrderSearchList(boolean showLoading);
    }
}
