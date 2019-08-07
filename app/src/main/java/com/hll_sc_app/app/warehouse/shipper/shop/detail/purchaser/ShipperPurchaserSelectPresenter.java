package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchaser;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
public class ShipperPurchaserSelectPresenter implements ShipperPurchaserSelectContract.IPurchaserSelectPresenter {
    private int mPageNum;
    private int mTempPageNum;
    private ShipperPurchaserSelectContract.IPurchaserSelectView mView;

    static ShipperPurchaserSelectPresenter newInstance() {
        return new ShipperPurchaserSelectPresenter();
    }

    @Override
    public void start() {
        queryPurchaserList(true);
    }

    @Override
    public void register(ShipperPurchaserSelectContract.IPurchaserSelectView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserList(showLoading);
    }

    @Override
    public void queryMorePurchaserList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserList(false);
    }

    private void toQueryPurchaserList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "unWarehouse")
            .put("searchParams", mView.getSearchParam())
            .put("pageNo", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("groupID", UserConfig.getGroupID())
            .create();
        WarehouseService.INSTANCE
            .queryWarehousePurchaserList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ShipperShopResp>() {
                @Override
                public void onSuccess(ShipperShopResp resp) {
                    mPageNum = mTempPageNum;
                    if (resp.getPageInfo() != null) {
                        mView.showPurchaserList(resp.getPurchaserList(), mPageNum != 1, resp.getPageInfo().getTotal());
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

}
