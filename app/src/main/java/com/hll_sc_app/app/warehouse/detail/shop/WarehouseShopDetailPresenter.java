package com.hll_sc_app.app.warehouse.detail.shop;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.warehouse.ShopParameterBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓货主门店详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public class WarehouseShopDetailPresenter implements WarehouseShopDetailContract.IWarehouseShopDetailPresenter {
    private WarehouseShopDetailContract.IWarehouseShopDetailView mView;

    static WarehouseShopDetailPresenter newInstance() {
        return new WarehouseShopDetailPresenter();
    }

    @Override
    public void register(WarehouseShopDetailContract.IWarehouseShopDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryWarehouseShop() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", mView.getPurchaserId())
            .put("shopIds", mView.getShopIds())
            .create();
        WarehouseService.INSTANCE
            .queryWarehouseShop(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<ShopParameterBean>>() {
                @Override
                public void onSuccess(List<ShopParameterBean> list) {
                    if (!CommonUtils.isEmpty(list)) {
                        mView.showDetail(list.get(0));
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void editWarehouseShop() {

    }
}
