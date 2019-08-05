package com.hll_sc_app.app.warehouse.detail.details;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓管理-代仓详情
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
    public void queryCooperationWarehouseDetail(String purchaserId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", purchaserId)
            .create();
        WarehouseService.INSTANCE
            .queryCooperationWarehouseDetail(req)
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
}
