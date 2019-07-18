package com.hll_sc_app.app.cooperation.detail.shopdelivery;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.DeliveryBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-选择配送方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
public class CooperationShopDeliveryPresenter implements CooperationShopDeliveryContract.ICooperationShopDeliveryPresenter {
    private CooperationShopDeliveryContract.ICooperationShopDeliveryView mView;

    static CooperationShopDeliveryPresenter newInstance() {
        return new CooperationShopDeliveryPresenter();
    }

    @Override
    public void start() {
        queryDeliveryList();
    }

    @Override
    public void register(CooperationShopDeliveryContract.ICooperationShopDeliveryView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryList() {
        BaseMapReq req = BaseMapReq
            .newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService
            .INSTANCE
            .queryDeliveryList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<DeliveryBean>() {
                @Override
                public void onSuccess(DeliveryBean resp) {
                    mView.showDeliveryList(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editShopDelivery(ShopSettlementReq req) {
        if (req == null) {
            return;
        }
        BaseReq<ShopSettlementReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService
            .INSTANCE
            .editShopSettlement(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object object) {
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
