package com.hll_sc_app.app.cooperation.detail.shopdetail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-门店详情
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public class CooperationShopDetailPresenter implements CooperationShopDetailContract.ICooperationShopDetailPresenter {
    private CooperationShopDetailContract.ICooperationShopDetailView mView;

    static CooperationShopDetailPresenter newInstance() {
        return new CooperationShopDetailPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(CooperationShopDetailContract.ICooperationShopDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
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
