package com.hll_sc_app.app.cooperation.detail.details;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-详细资料
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public abstract class BaseCooperationDetailsFragment extends BaseLazyFragment implements CooperationButtonView.Listener {

    @Override
    public void del(CooperationPurchaserDetail detail) {
        // 解除合作
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", detail.getPurchaserID())
            .create();
        CooperationPurchaserService.INSTANCE.delCooperationPurchaser(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> showLoading())
            .doFinally(this::hideLoading)
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    showToast("解除合作成功");
                    editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    showError(e);
                }
            });
    }

    public void editSuccess() {
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_LIST)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(requireActivity());
    }
}
