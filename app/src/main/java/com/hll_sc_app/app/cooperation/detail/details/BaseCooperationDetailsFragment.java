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
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.widget.RemarkDialog;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-详细资料
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public abstract class BaseCooperationDetailsFragment extends BaseLazyFragment implements CooperationButtonView.Listener {

    public static final String FROM_COOPERATION_DETAILS_AGREE = "FROM_COOPERATION_DETAILS_AGREE";
    public static final String FROM_COOPERATION_DETAILS_ADD = "FROM_COOPERATION_DETAILS_ADD";
    public static final String FROM_COOPERATION_DETAILS_REPEAT = "FROM_COOPERATION_DETAILS_REPEAT";
    /**
     * 修改集团级别的属性
     */
    public static final String FROM_COOPERATION_DETAILS_PROPERTY = "FROM_COOPERATION_DETAILS_PROPERTY";

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

    @Override
    public void reject(CooperationPurchaserDetail detail) {
        RemarkDialog.newBuilder(requireActivity())
            .setHint("可输入驳回理由，选填")
            .setMaxLength(100)
            .setButtons("容我再想想", "确认拒绝", (dialog, positive, content) -> {
                dialog.dismiss();
                if (positive) {
                    rejectRequest(content, detail);
                }
            })
            .create()
            .show();
    }

    @Override
    public void agree(CooperationPurchaserDetail detail) {
        ShopSettlementReq req = new ShopSettlementReq();
        req.setFrom(FROM_COOPERATION_DETAILS_AGREE);
        req.setPurchaserID(detail.getPurchaserID());
        // 同意之前先选择结算方式
        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, req);
    }

    @Override
    public void add(CooperationPurchaserDetail detail) {
        ShopSettlementReq req = new ShopSettlementReq();
        req.setPurchaserName(detail.getName());
        req.setFrom(FROM_COOPERATION_DETAILS_ADD);
        req.setPurchaserID(detail.getPurchaserID());
        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, req);
    }

    @Override
    public void repeat(CooperationPurchaserDetail detail) {
        ShopSettlementReq req = new ShopSettlementReq();
        req.setPurchaserName(detail.getName());
        req.setFrom(FROM_COOPERATION_DETAILS_REPEAT);
        req.setPurchaserID(detail.getPurchaserID());
        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, req);
    }

    private void rejectRequest(String reply, CooperationPurchaserDetail detail) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "refuse")
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", detail.getPurchaserID())
            .put("reply", reply)
            .create();
        CooperationPurchaserService.INSTANCE.editCooperationPurchaser(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> showLoading())
            .doFinally(this::hideLoading)
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    showToast("拒绝合作成功");
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

    /**
     * 刷新 Fragment
     *
     * @param detail detail
     */
    protected abstract void refreshFragment(CooperationPurchaserDetail detail);
}
