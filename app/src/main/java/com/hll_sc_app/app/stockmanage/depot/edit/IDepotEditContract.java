package com.hll_sc_app.app.stockmanage.depot.edit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.DepotResp;

public interface IDepotEditContract {
    interface IDepotEditView extends ILoadView {
        DepotResp getDepot();

        void saveSuccess();
    }

    interface IDepotEditPresenter extends IPresenter<IDepotEditView> {
        void saveStoreHouseInfo();
    }
}
