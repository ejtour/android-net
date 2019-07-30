package com.hll_sc_app.app.deliverymanage.ageing.detail.period;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.bean.delivery.DeliveryPeriodResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
    public void queryDeliveryPeriodList() {
        getListObservable()
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryPeriodRespBaseCallback());
    }

    private Observable<DeliveryPeriodResp> getListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("billUpDateTime", mView.getBillUpDateTime())
            .put("flg", mView.getFlag())
            .put("groupID", UserConfig.getGroupID())
            .create();
        return DeliveryManageService.INSTANCE
            .queryDeliveryPeriodList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    @Override
    public void editDeliveryPeriod(DeliveryPeriodBean bean, String operationType) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("operationType", operationType)
            .put("groupID", UserConfig.getGroupID())
            .put("deliveryTimeID", bean.getDeliveryTimeID())
            .put("arrivalStartTime", bean.getArrivalStartTime())
            .put("arrivalEndTime", bean.getArrivalEndTime())
            .create();
        DeliveryManageService.INSTANCE
            .editDeliveryPeriod(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<DeliveryPeriodResp>>) o -> {
                mView.showToast("配送时段" + getOperationString(operationType) + "成功");
                return getListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryPeriodRespBaseCallback());
    }

    private String getOperationString(String operationType) {
        String string = null;
        switch (operationType) {
            case "0":
                string = "新增";
                break;
            case "1":
                string = "更新";
                break;
            case "2":
                string = "删除";
                break;
            default:
                break;
        }
        return string;
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
