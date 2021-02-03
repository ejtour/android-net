package com.hll_sc_app.app.warehouse.shipper.shop.detail;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.bean.warehouse.WarehousePurchaserEditReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓门店管理详情
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
public class ShipperShopDetailPresenter implements ShipperShopDetailContract.IShipperShopDetailPresenter {
    private int mPageNum;
    private int mTempPageNum;
    private ShipperShopDetailContract.IShipperShopDetailView mView;

    static ShipperShopDetailPresenter newInstance() {
        return new ShipperShopDetailPresenter();
    }

    @Override
    public void start() {
        queryWarehouseList(true);
    }

    @Override
    public void register(ShipperShopDetailContract.IShipperShopDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryWarehouseList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryWarehouseList(showLoading);
    }

    @Override
    public void queryMoreWarehouseList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryWarehouseList(false);
    }

    @Override
    public void editWarehousePurchaser(ShipperShopResp.PurchaserBean bean, String actionType) {
        WarehousePurchaserEditReq req = new WarehousePurchaserEditReq();
        req.setActionType(actionType);
        req.setGroupID(UserConfig.getGroupID());
        req.setWarehouseID(mView.getWarehouseId());
        List<WarehousePurchaserEditReq.PurchaserId> list = new ArrayList<>();
        list.add(new WarehousePurchaserEditReq.PurchaserId(bean.getPurchaserID()));
        req.setPurchaserList(list);
        WarehouseService.INSTANCE
            .editWarehousePurchaser(new BaseReq<>(req))
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("代仓编辑合作客户成功");
                    queryWarehouseList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryWarehouseList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "warehouse")
            .put("searchParams", mView.getSearchParam())
            .put("pageNo", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("groupID", UserConfig.getGroupID())
            .put("warehouseID", mView.getWarehouseId())
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
                        mView.showWarehouseList(resp.getPurchaserList(), mPageNum != 1, resp.getPageInfo().getTotal());
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

}
