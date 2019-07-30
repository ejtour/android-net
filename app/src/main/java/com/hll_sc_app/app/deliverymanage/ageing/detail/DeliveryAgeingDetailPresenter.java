package com.hll_sc_app.app.deliverymanage.ageing.detail;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 配送时效编辑、新增界面
 *
 * @author zhuyingsong
 * @date 2019/7/29
 */
public class DeliveryAgeingDetailPresenter implements DeliveryAgeingDetailContract.IDeliveryAgeingDetailPresenter {
    private DeliveryAgeingDetailContract.IDeliveryAgeingDetailView mView;

    static DeliveryAgeingDetailPresenter newInstance() {
        return new DeliveryAgeingDetailPresenter();
    }

    @Override
    public void register(DeliveryAgeingDetailContract.IDeliveryAgeingDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editDeliveryAgeing(BaseMapReq req) {
        DeliveryManageService.INSTANCE
            .editDeliveryAgeing(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
