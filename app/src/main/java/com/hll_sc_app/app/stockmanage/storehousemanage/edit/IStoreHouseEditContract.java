package com.hll_sc_app.app.stockmanage.storehousemanage.edit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;

public interface IStoreHouseEditContract {
    interface IView extends ILoadView {
        StorehouseListResp.Storehouse getStoreHouse();

        void saveSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void saveStoreHouseInfo();
    }

}
