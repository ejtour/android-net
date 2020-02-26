package com.hll_sc_app.app.warehouse.detail.shop;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.warehouse.ShopParameterBean;
import com.hll_sc_app.bean.warehouse.WarehouseSettlementBean;
import com.hll_sc_app.bean.warehouse.WarehouseSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
        getWarehouseShopObservable()
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new ListBaseCallback());
    }

    private Observable<List<ShopParameterBean>> getWarehouseShopObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", mView.getPurchaserId())
            .put("shopIds", mView.getShopIds())
            .create();
        return WarehouseService.INSTANCE
            .queryWarehouseShop(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    @Override
    public void editWarehouseShop(String supportPay) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", mView.getPurchaserId())
            .put("shopId", mView.getShopIds())
            .put("supportPay", supportPay)
            .create();
        WarehouseService.INSTANCE
            .editWarehouseShop(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<List<ShopParameterBean>>>) o -> {
                mView.showToast("操作成功");
                return getWarehouseShopObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new ListBaseCallback());
    }

    private class ListBaseCallback extends BaseCallback<List<ShopParameterBean>> {
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
    }

    @Override
    public void getWarehouseSettlement(String purchaserID, String shopIds) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseReq<WarehouseSettlementReq> baseReq = new BaseReq<>();
        WarehouseSettlementReq.GetSettlementWayListReqBean bean = new WarehouseSettlementReq.GetSettlementWayListReqBean();
        bean.setGroupID(userBean.getGroupID());
        bean.setShopID(shopIds);
        bean.setPurchaserID(purchaserID);
        WarehouseSettlementReq req = new WarehouseSettlementReq();
        req.setGetSettlementWayListReq(Collections.singletonList(bean));
        baseReq.setData(req);
        WarehouseService.INSTANCE
                .getWarehouseSettlement(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                })
                .subscribe(new BaseCallback<List<WarehouseSettlementBean>>() {
                    @Override
                    public void onSuccess(List<WarehouseSettlementBean> warehouseSettlementBeans) {
                        if (mView.isActive()) {
                            mView.showPayType(warehouseSettlementBeans);
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (mView.isActive()) {
                            mView.showError(e);
                        }
                    }
                });
    }
}
