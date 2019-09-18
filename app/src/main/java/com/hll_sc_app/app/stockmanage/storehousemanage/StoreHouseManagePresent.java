package com.hll_sc_app.app.stockmanage.storehousemanage;

import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class StoreHouseManagePresent implements IStoreHouseManageContract.IPresent {
    IStoreHouseManageContract.IView mView;

    private int pageSize = 20;
    private int pageNum = 1;
    private int pageNumTemp = 1;

    public static StoreHouseManagePresent newInstance() {
        return new StoreHouseManagePresent();
    }

    @Override
    public void register(IStoreHouseManageContract.IView view) {
        mView = view;
    }

    @Override
    public void getStoreHouseList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .put("pageNum", pageNumTemp + "")
                .put("pageSize", pageSize + "")
                .create();

        StockManageService.INSTANCE
                .getStoreHouseList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<StorehouseListResp>() {
                    @Override
                    public void onSuccess(StorehouseListResp storehouseListResp) {
                        mView.showStoreHouseList(storehouseListResp, pageNumTemp > 1);
                        pageNum = pageNumTemp;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageNumTemp = pageNum;
                        mView.showError(e);
                    }
                });

    }

    @Override
    public void getMoreList() {
        pageNumTemp++;
        getStoreHouseList(false);
    }

    @Override
    public void refreshList() {
        pageNumTemp = 1;
        getStoreHouseList(false);
    }
}
