package com.hll_sc_app.app.paymanage.account;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.app.paymanage.PayManagePresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
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
public class PayAccountManagePresenter implements PayAccountManageContract.IAccountPresenter {
    private PayAccountManageContract.IAccountView mView;

    static PayAccountManagePresenter newInstance() {
        return new PayAccountManagePresenter();
    }

    @Override
    public void register(PayAccountManageContract.IAccountView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editAccount(String payTermType, String payTerm, String settleDate) {
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

    @Override
    public void toggle(boolean checked) {
        PayManagePresenter.editSettlement("2", checked ? "1" : "0", new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.toggleSuccess();
            }

            @Override
            public void onFailure(UseCaseException e) {
                super.onFailure(e);
                mView.toggleFailure();
            }
        });
    }
}
