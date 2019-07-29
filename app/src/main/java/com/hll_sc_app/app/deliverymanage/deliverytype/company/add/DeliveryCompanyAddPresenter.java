package com.hll_sc_app.app.deliverymanage.deliverytype.company.add;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.app.deliverymanage.deliverytype.DeliveryTypeSetPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.DeliveryBean;
import com.hll_sc_app.bean.delivery.DeliveryCompanyReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择第三方物流公司
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
public class DeliveryCompanyAddPresenter implements DeliveryCompanyAddContract.IDeliveryCompanyAddPresenter {
    private DeliveryCompanyAddContract.IDeliveryCompanyAddView mView;

    static DeliveryCompanyAddPresenter newInstance() {
        return new DeliveryCompanyAddPresenter();
    }

    @Override
    public void register(DeliveryCompanyAddContract.IDeliveryCompanyAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void addDeliveryCompany(List<String> list) {
        if (CommonUtils.isEmpty(list)) {
            mView.showToast("请输入新增合作物流公司的名称");
            return;
        }
        DeliveryCompanyReq req = new DeliveryCompanyReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setDeliveryCompanyName(list);
        BaseReq<DeliveryCompanyReq> baseReq = new BaseReq<>(req);
        DeliveryManageService.INSTANCE
            .addDeliveryCompany(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<Object, ObservableSource<DeliveryBean>>) o -> {
                mView.showToast("添加三方配送公司成功");
                return DeliveryTypeSetPresenter.getDeliveryListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<DeliveryBean>() {
                @Override
                public void onSuccess(DeliveryBean bean) {
                    EventBus.getDefault().post(CommonUtils.deepClone(bean.getDeliveryCompanyList()));
                    mView.showCompanyList(bean.getDeliveryCompanyList());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
