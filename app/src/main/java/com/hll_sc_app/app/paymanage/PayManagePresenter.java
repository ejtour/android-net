package com.hll_sc_app.app.paymanage;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public class PayManagePresenter implements PayManageContract.IDeliveryTypeSetPresenter {
    private PayManageContract.IDeliveryTypeSetView mView;

    static PayManagePresenter newInstance() {
        return new PayManagePresenter();
    }

    @Override
    public void start() {
        querySettlementList();
    }

    @Override
    public void register(PayManageContract.IDeliveryTypeSetView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySettlementList() {
        getSettlementListObservable().doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new SettlementBeanBaseCallback());
    }

    @Override
    public void editSettlement(String payType, String status) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "1")
            .put("payType", payType)
            .put("status", status)
            .put("supplierID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService.INSTANCE
            .editSettlement(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<SettlementBean>>) o -> {
                mView.showToast("支付方式修改成功");
                return getSettlementListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new SettlementBeanBaseCallback());
    }

    private Observable<SettlementBean> getSettlementListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("supplyID", UserConfig.getGroupID())
            .create();
        return CooperationPurchaserService.INSTANCE
            .querySettlementList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    private class SettlementBeanBaseCallback extends BaseCallback<SettlementBean> {
        @Override
        public void onSuccess(SettlementBean settlementBean) {
            mView.showPayList(settlementBean);
        }

        @Override
        public void onFailure(UseCaseException e) {
            mView.showError(e);
            // 失败处理
            mView.showPayList();
        }
    }
}
