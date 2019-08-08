package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchasershop;

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
 * 选择合作采购商-选择门店
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public class ShipperPurchaserShopSelectPresenter implements ShipperPurchaserShopSelectContract.IShopListPresenter {
    private int mPageNum;
    private int mTempPageNum;
    private ShipperPurchaserShopSelectContract.IShopListView mView;

    static ShipperPurchaserShopSelectPresenter newInstance() {
        return new ShipperPurchaserShopSelectPresenter();
    }

    @Override
    public void start() {
        queryShopList(true);
    }

    @Override
    public void register(ShipperPurchaserShopSelectContract.IShopListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryShopList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryShopList(showLoading);
    }

    @Override
    public void queryMoreShopList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryShopList(false);
    }

    @Override
    public void editWarehousePurchaser(List<String> list) {
        ShipperShopResp.PurchaserBean purchaserBean = mView.getPurchaserBean();
        if (CommonUtils.isEmpty(list) || purchaserBean == null) {
            return;
        }
        WarehousePurchaserEditReq req = new WarehousePurchaserEditReq();
        req.setActionType("insert");
        req.setGroupID(UserConfig.getGroupID());
        req.setWarehouseID(purchaserBean.getWarehouseId());
        List<WarehousePurchaserEditReq.PurchaserId> purchaserIds = new ArrayList<>();
        WarehousePurchaserEditReq.PurchaserId purchaser =
            new WarehousePurchaserEditReq.PurchaserId(purchaserBean.getPurchaserID());
        purchaser.setShopIDs(list);
        purchaserIds.add(purchaser);
        req.setPurchaserList(purchaserIds);
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
                    mView.showToast("代仓编辑合作采购商成功");
                    mView.editSuccess();

                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryShopList(boolean showLoading) {
        ShipperShopResp.PurchaserBean purchaserBean = mView.getPurchaserBean();
        if (purchaserBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "unWarehouse")
            .put("searchParams", mView.getSearchParam())
            .put("pageNo", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", purchaserBean.getPurchaserID())
            .create();
        WarehouseService.INSTANCE
            .queryWarehousePurchaserShopList(req)
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
                        mView.showShopList(resp.getShopList(), mPageNum != 1, resp.getPageInfo()
                            .getTotal());
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
