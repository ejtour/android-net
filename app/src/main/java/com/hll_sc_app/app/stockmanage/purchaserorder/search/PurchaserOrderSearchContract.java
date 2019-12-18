package com.hll_sc_app.app.stockmanage.purchaserorder.search;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchBean;

import java.util.List;

/**
 * 采购单搜索
 * @author chukun
 * @date 2019/9/10
 */
public interface PurchaserOrderSearchContract {

    interface IPurchaserOrderSearchView extends ILoadView {
        void setData(List<PurchaserOrderSearchBean> list, boolean append);

        String getSearchKey();
    }

    interface IPurchaserOrderSearchPresenter extends IPresenter<IPurchaserOrderSearchView> {
        void loadMore();
    }
}
