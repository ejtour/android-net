package com.hll_sc_app.app.deliverymanage.ageing;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryPeriodResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 配送时效管理-配送时效
 *
 * @author 朱英松
 * @date 2019/7/29
 */
public class DeliveryAgeingFragmentPresenter implements DeliveryAgeingFragmentContract.IDeliveryAgeingBookPresenter {
    private DeliveryAgeingFragmentContract.IDeliveryAgeingBookView mView;

    public static DeliveryAgeingFragmentPresenter newInstance() {
        return new DeliveryAgeingFragmentPresenter();
    }

    @Override
    public void start() {
        queryDeliveryAgeingList();
    }

    @Override
    public void register(DeliveryAgeingFragmentContract.IDeliveryAgeingBookView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryAgeingList() {
        getListObservable()
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .subscribe(new DeliveryPeriodRespBaseCallback());
    }

    @Override
    public void delAgeing(String deliveryTimeId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("operationType", "2")
            .put("groupID", UserConfig.getGroupID())
            .put("deliveryTimeID", deliveryTimeId)
            .create();
        DeliveryManageService.INSTANCE
            .delDeliveryAgeing(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<DeliveryPeriodResp>>) o -> {
                mView.showToast("删除成功");
                return getListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .subscribe(new DeliveryPeriodRespBaseCallback());
    }

    private Observable<DeliveryPeriodResp> getListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .create();
        return DeliveryManageService.INSTANCE
            .queryDeliveryPeriodList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    private class DeliveryPeriodRespBaseCallback extends BaseCallback<DeliveryPeriodResp> {
        @Override
        public void onSuccess(DeliveryPeriodResp resp) {
            mView.showList(resp.getRecords());
        }

        @Override
        public void onFailure(UseCaseException e) {
            mView.showToast(e.getMessage());
        }
    }
}
