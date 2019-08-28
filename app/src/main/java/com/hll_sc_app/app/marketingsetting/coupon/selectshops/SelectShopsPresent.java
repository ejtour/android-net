package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationShopsListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectShopsPresent implements ISelectContract.ISearchShopsPresent {
    private ISelectContract.ISearchShopsView mView;

    public static SelectShopsPresent newInstance() {
        return new SelectShopsPresent();
    }

    @Override
    public void register(ISelectContract.ISearchShopsView view) {
        mView = view;
    }


    @Override
    public void getShops() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "9999")
                .put("searchParams", mView.getSearchText())
                .put("purchaserID", mView.getPurchaserID())
                .put("actionType", "coupon").create();

        CooperationPurchaserService.INSTANCE
                .getCooperationShops(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CooperationShopsListResp>() {
                    @Override
                    public void onSuccess(CooperationShopsListResp cooperationShopsListResp) {
                        mView.showShops(cooperationShopsListResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
