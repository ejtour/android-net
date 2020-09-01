package com.hll_sc_app.app.deliverymanage.minimum;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * 起送金额列表
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
public class DeliveryMinimumPresenter implements DeliveryMinimumContract.IDeliveryMinimumPresenter {
    private DeliveryMinimumContract.IDeliveryMinimumView mView;

    static DeliveryMinimumPresenter newInstance() {
        return new DeliveryMinimumPresenter();
    }

    @Override
    public void start() {
        queryDeliveryMinimumList();
    }

    @Override
    public void register(DeliveryMinimumContract.IDeliveryMinimumView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryMinimumList() {
        getDeliveryListObservable()
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryMinimumBaseCallback());
    }

    @Override
    public void delDeliveryMinimum(DeliveryMinimumBean bean) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("supplyID", bean.getSupplyID())
            .put("settings", bean.getSettings())
            .put("sendAmountID", bean.getSendAmountID())
            .put("supplyShopID", bean.getSupplyShopID())
            .create();
        DeliveryManageService.INSTANCE
            .delDeliveryMinimum(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<List<DeliveryMinimumBean>>>) o -> {
                mView.showToast("删除起送金额分组成功");
                return getDeliveryListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new DeliveryMinimumBaseCallback());
    }


    private Observable<List<DeliveryMinimumBean>> getDeliveryListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("supplyID", UserConfig.getGroupID())
                .put("purchaserID", mView.getPurchaserID())
                .put("purchaserShopID", mView.getPurchaserShopID())
                .create();
        return DeliveryManageService.INSTANCE
            .queryDeliveryMinimumList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    private class DeliveryMinimumBaseCallback extends BaseCallback<List<DeliveryMinimumBean>> {
        @Override
        public void onSuccess(List<DeliveryMinimumBean> resp) {
            mView.showDeliveryList(resp);
        }

        @Override
        public void onFailure(UseCaseException e) {
            mView.showError(e);
        }
    }
}
