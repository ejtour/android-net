package com.hll_sc_app.app.deliverymanage.deliverytype.company;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryCompanyReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择第三方物流公司
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
public class DeliveryCompanyPresenter implements DeliveryCompanyContract.IDeliveryCompanyPresenter {
    private DeliveryCompanyContract.IDeliveryCompanyView mView;
    private int mPageNum;
    private int mTempPageNum;

    static DeliveryCompanyPresenter newInstance() {
        return new DeliveryCompanyPresenter();
    }

    @Override
    public void register(DeliveryCompanyContract.IDeliveryCompanyView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryCompanyList() {
    }

    @Override
    public void editDeliveryCompanyStatus(List<String> list) {
        if (CommonUtils.isEmpty(list)) {
            mView.showToast("至少保留一个三方配送公司");
            return;
        }
        DeliveryCompanyReq req = new DeliveryCompanyReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setDeliveryCompanyIDs(list);
        BaseReq<DeliveryCompanyReq> baseReq = new BaseReq<>(req);
        DeliveryManageService.INSTANCE
            .editDeliveryCompanyStatus(baseReq)
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
