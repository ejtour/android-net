package com.hll_sc_app.app.paymanage.method;

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
public class PayMethodManagePresenter implements PayMethodManageContract.IMethodPresenter {
    private PayMethodManageContract.IMethodView mView;

    static PayMethodManagePresenter newInstance() {
        return new PayMethodManagePresenter();
    }

    @Override
    public void register(PayMethodManageContract.IMethodView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editPayMethod(String payType, String payMethod) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("payMethod", payMethod)
            .put("payType", payType)
            .put("supplyID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService.INSTANCE
            .editSettlementMethod(req)
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
