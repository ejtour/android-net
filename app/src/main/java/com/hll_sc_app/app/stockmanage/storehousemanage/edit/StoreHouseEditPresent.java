package com.hll_sc_app.app.stockmanage.storehousemanage.edit;

import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class StoreHouseEditPresent implements IStoreHouseEditContract.IPresent {
    private IStoreHouseEditContract.IView mView;

    public static StoreHouseEditPresent newInstance() {
        return new StoreHouseEditPresent();
    }

    @Override
    public void register(IStoreHouseEditContract.IView view) {
        mView = view;
    }

    @Override
    public void saveStoreHouseInfo() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }

        StorehouseListResp.Storehouse storehouse = mView.getStoreHouse();
        storehouse.setGroupID(userBean.getGroupID());
        BaseReq<StorehouseListResp.Storehouse> baseReq = new BaseReq<>();
        baseReq.setData(storehouse);
        StockManageService.INSTANCE
                .saveStoreHouseInfo(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.saveSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
