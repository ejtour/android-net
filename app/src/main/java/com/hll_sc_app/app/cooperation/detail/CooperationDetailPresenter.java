package com.hll_sc_app.app.cooperation.detail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class CooperationDetailPresenter implements CooperationDetailContract.IGoodsRelevancePurchaserPresenter {
    private CooperationDetailContract.IGoodsRelevancePurchaserView mView;

    static CooperationDetailPresenter newInstance() {
        return new CooperationDetailPresenter();
    }

    @Override
    public void start() {
        queryPurchaserDetail();
    }

    @Override
    public void register(CooperationDetailContract.IGoodsRelevancePurchaserView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserDetail() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("originator", "1")
            .put("pageNo", "1")
            .put("pageSize", "20")
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", mView.getPurchaserId())
            .create();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                mView.showLoading();
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CooperationPurchaserDetail>() {
                @Override
                public void onSuccess(CooperationPurchaserDetail resp) {
                    mView.showPurchaserDetail(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }


    @Override
    public void delCooperationPurchaser(String purchaserId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", purchaserId)
            .create();
        CooperationPurchaserService.INSTANCE.delCooperationPurchaser(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
