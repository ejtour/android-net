package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectGroupsPresent implements ISelectContract.ISearchGroupsPresent {

    private ISelectContract.ISearchGroupsView mView;

    public static SelectGroupsPresent newInstance() {
        return new SelectGroupsPresent();
    }

    @Override
    public void register(ISelectContract.ISearchGroupsView view) {
        mView = view;
    }

    @Override
    public void getGroups() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "formalCooperation")
                .put("groupID", UserConfig.getGroupID())
                .put("name", mView.getSearchText())
                .put("pageNo", "1")
                .put("pageSize", "9999")
                .put("originator", "1")
                .create();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CooperationPurchaserResp>() {
                    @Override
                    public void onSuccess(CooperationPurchaserResp resp) {
                        mView.showGroups(resp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
