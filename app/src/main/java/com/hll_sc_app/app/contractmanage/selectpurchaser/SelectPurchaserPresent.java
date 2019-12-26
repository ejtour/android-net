package com.hll_sc_app.app.contractmanage.selectpurchaser;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectPurchaserPresent implements ISelectPurchaserContract.IPresent {

    private ISelectPurchaserContract.IView mView;

    static SelectPurchaserPresent newInstance() {
        return new SelectPurchaserPresent();
    }

    @Override
    public void register(ISelectPurchaserContract.IView view) {
        mView = view;
    }

    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        CommonService.INSTANCE
                .queryPurchaserList(BaseMapReq.newBuilder()
                        .put("groupID", userBean.getGroupID())
                        .put("ignoreGroupActive", "0")
                        .put("searchParam", mView.getSearchText())
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<PurchaserBean>>() {
                    @Override
                    public void onSuccess(List<PurchaserBean> purchaserBeans) {
                        mView.querySuccess(purchaserBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


}
