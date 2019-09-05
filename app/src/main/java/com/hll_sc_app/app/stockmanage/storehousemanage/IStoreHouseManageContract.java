package com.hll_sc_app.app.stockmanage.storehousemanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;

public interface IStoreHouseManageContract {

    interface IView extends ILoadView {
        void showStoreHouseList(StorehouseListResp storehouseListResp, boolean isMore);
    }

    interface IPresent extends IPresenter<IView> {
        void getStoreHouseList(boolean isLoading);

        void getMoreList();

        void refreshList();
    }
}
