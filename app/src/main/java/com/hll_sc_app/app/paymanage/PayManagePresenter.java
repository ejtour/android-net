package com.hll_sc_app.app.paymanage;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryBean;
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
//        queryDeliveryList();
    }

    @Override
    public void register(PayManageContract.IDeliveryTypeSetView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryList() {
        getDeliveryListObservable().doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryBeanBaseCallback());
    }

    @Override
    public void editDeliveryType(String actionType, String deliveryWay) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", actionType)
            .put("deliveryWay", deliveryWay)
            .put("groupID", UserConfig.getGroupID())
            .create();
        DeliveryManageService.INSTANCE
            .editDeliveryType(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<DeliveryBean>>) o -> {
                mView.showToast("配送方式修改成功");
                return getDeliveryListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryBeanBaseCallback());
    }

    public static Observable<DeliveryBean> getDeliveryListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .create();
        return DeliveryManageService.INSTANCE
            .queryDeliveryList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    private class DeliveryBeanBaseCallback extends BaseCallback<DeliveryBean> {
        @Override
        public void onSuccess(DeliveryBean resp) {
            mView.showDeliveryList(resp);
        }

        @Override
        public void onFailure(UseCaseException e) {
            mView.showError(e);
        }
    }
}
