package com.hll_sc_app.app.warehouse.detail.details;

import android.text.TextUtils;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.ShopParamsReq;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Arrays;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓管理-代仓客户详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public class WarehouseDetailsPresenter implements WarehouseDetailsContract.IWarehouseDetailsPresenter {
    private WarehouseDetailsContract.IWarehouseDetailsView mView;

    static WarehouseDetailsPresenter newInstance() {
        return new WarehouseDetailsPresenter();
    }

    @Override
    public void register(WarehouseDetailsContract.IWarehouseDetailsView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationWarehouseDetail(String associateID) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        if (UserConfig.isSelfOperated()) {
            // 如果为自营则是代仓公司
            builder
                    .put("groupID", UserConfig.getGroupID())
                    .put(TextUtils.isEmpty(mView.getReqKey()) ? "purchaserID" :
                            mView.getReqKey(), associateID)
                    .put("originator", "1");
        } else {
            // 非自营则是货主
            builder
                    .put(TextUtils.isEmpty(mView.getReqKey()) ? "groupID" :
                            mView.getReqKey(), associateID)
                    .put("purchaserID", UserConfig.getGroupID())
                    .put("originator", "0");
        }
        WarehouseService.INSTANCE
            .queryCooperationWarehouseDetail(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<WarehouseDetailResp>() {
                @Override
                public void onSuccess(WarehouseDetailResp result) {
                    mView.showDetail(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void addWarehouse(BaseMapReq req) {
        WarehouseService.INSTANCE
            .addWarehouse(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    mView.showToast("已发送申请等待对方同意");
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void delWarehouse(BaseMapReq req, String type) {
        WarehouseService.INSTANCE
            .delWarehouse(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    if (TextUtils.equals(type, "1")) {
                        mView.showToast("解除签约关系成功");
                    } else {
                        mView.showToast("放弃代仓成功");
                    }
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void agreeOrRefuseWarehouse(BaseMapReq req, String type) {
        WarehouseService.INSTANCE
            .agreeOrRefuseWarehouse(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    if (TextUtils.equals(type, "agree")) {
                        mView.showToast("已同意");
                    } else {
                        mView.showToast("已拒绝");
                    }
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editWarehouseParameter(BaseMapReq req) {
        WarehouseService.INSTANCE
            .editWarehouseParameter(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    mView.showToast("操作成功");
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void changeShopParams(String purchaserId, String supportPay, String payee) {
        BaseReq<ShopParamsReq> baseReq = new BaseReq<>();
        ShopParamsReq req = new ShopParamsReq();
        req.setPurchaserID(UserConfig.getGroupID());
        req.setGroupID(purchaserId);

        ShopParamsReq.Param param1 = new ShopParamsReq.Param("supportPay", supportPay);
        ShopParamsReq.Param param2 = new ShopParamsReq.Param("payee", payee);
        req.setBizList(Arrays.asList(param1, param2));
        baseReq.setData(req);
        CommonService.INSTANCE
                .changeGroupParams(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.changePayTypeResult(true,supportPay,payee);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.changePayTypeResult(false,supportPay,payee);
                    }
                });

    }
}
