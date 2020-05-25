package com.hll_sc_app.app.stockmanage.depot.edit;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.rest.Stock;

public class DepotEditPresenter implements IDepotEditContract.IDepotEditPresenter {
    private IDepotEditContract.IDepotEditView mView;

    public static DepotEditPresenter newInstance() {
        return new DepotEditPresenter();
    }

    @Override
    public void register(IDepotEditContract.IDepotEditView view) {
        mView = view;
    }

    @Override
    public void saveStoreHouseInfo() {
        DepotResp storehouse = mView.getDepot();
        storehouse.setGroupID(UserConfig.getGroupID());
        Stock.saveDepotInfo(storehouse, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.saveSuccess();
            }
        });
    }
}
