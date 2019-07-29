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
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .create();
        DeliveryManageService.INSTANCE
            .queryDeliveryPeriodList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .subscribe(new BaseCallback<DeliveryPeriodResp>() {
                @Override
                public void onSuccess(DeliveryPeriodResp resp) {
                    mView.showList(resp.getRecords());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

}
