package com.hll_sc_app.app.deliverymanage.ageing.detail.period;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryPeriodResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择配送时段
 *
 * @author zhuyingsong
 * @date 2019/7/29
 */
public class DeliveryPeriodPresenter implements DeliveryPeriodContract.IDeliveryPeriodPresenter {
    private DeliveryPeriodContract.IDeliveryPeriodView mView;

    static DeliveryPeriodPresenter newInstance() {
        return new DeliveryPeriodPresenter();
    }

    @Override
    public void register(DeliveryPeriodContract.IDeliveryPeriodView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryPeriodList(String billUpDateTime, String flg) {
        getListObservable(billUpDateTime, flg)
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryPeriodRespBaseCallback());
    }

    private Observable<DeliveryPeriodResp> getListObservable(String billUpDateTime, String flg) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("billUpDateTime", billUpDateTime)
            .put("flg", flg)
            .put("groupID", UserConfig.getGroupID())
            .create();
        return DeliveryManageService.INSTANCE
            .queryDeliveryPeriodList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    @Override
    public void editDeliveryAgeing(BaseMapReq req) {

    }

    private class DeliveryPeriodRespBaseCallback extends BaseCallback<DeliveryPeriodResp> {
        @Override
        public void onSuccess(DeliveryPeriodResp resp) {
            mView.showList(resp.getRecords());
        }

        @Override
        public void onFailure(UseCaseException e) {
            mView.showError(e);
        }
    }
}
