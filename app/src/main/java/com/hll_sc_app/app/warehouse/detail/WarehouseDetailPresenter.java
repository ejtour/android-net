package com.hll_sc_app.app.warehouse.detail;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.user.FollowQRResp;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓货主详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public class WarehouseDetailPresenter implements WarehouseDetailContract.IWarehouseDetailPresenter {
    private WarehouseDetailContract.IWarehouseDetailView mView;

    static WarehouseDetailPresenter newInstance() {
        return new WarehouseDetailPresenter();
    }

    @Override
    public void register(WarehouseDetailContract.IWarehouseDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationWarehouseDetail(String purchaserId) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        if (UserConfig.isSelfOperated()) {
            // 如果为自营则是代仓公司
            builder
                .put("groupID", UserConfig.getGroupID())
                .put("purchaserID", purchaserId)
                .put("originator", "1");
        } else {
            // 非自营则是货主
            builder
                .put("groupID", purchaserId)
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
    public void queryFollowQR(String groupID, String isWarehouse) {
        User.queryFollowQR(groupID, isWarehouse, new SimpleObserver<FollowQRResp>(mView) {
            @Override
            public void onSuccess(FollowQRResp followQRResp) {
                mView.showFollowDialog(followQRResp.getQrcodeUrl());
            }
        });
    }
}
