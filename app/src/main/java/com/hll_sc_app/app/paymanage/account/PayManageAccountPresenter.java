package com.hll_sc_app.app.paymanage.account;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 支付管理-设置账期日
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
public class PayManageAccountPresenter implements PayManageAccountContract.IAccountPresenter {
    private PayManageAccountContract.IAccountView mView;

    static PayManageAccountPresenter newInstance() {
        return new PayManageAccountPresenter();
    }

    @Override
    public void register(PayManageAccountContract.IAccountView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editSettlement(String payTermType, String payTerm, String settleDate) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "2")
            .put("payTermType", payTermType)
            .put("payTerm", payTerm)
            .put("payType", "2")
            .put("settleDate", settleDate)
            .put("status", "1")
            .put("supplierID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService.INSTANCE
            .editSettlement(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object settlementBean) {
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
