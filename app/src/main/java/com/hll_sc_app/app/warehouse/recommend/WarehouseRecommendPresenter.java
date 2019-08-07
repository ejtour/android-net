package com.hll_sc_app.app.warehouse.recommend;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓管理-我是货主默认介绍页面-查看推荐
 *
 * @author zhuyingsong
 * @date 2019/8/2
 */
public class WarehouseRecommendPresenter implements WarehouseRecommendContract.IWarehouseRecommendPresenter {
    private WarehouseRecommendContract.IWarehouseRecommendView mView;

    static WarehouseRecommendPresenter newInstance() {
        return new WarehouseRecommendPresenter();
    }

    @Override
    public void start() {
        getRecommendWarehouse();
    }

    @Override
    public void register(WarehouseRecommendContract.IWarehouseRecommendView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void getRecommendWarehouse() {
        WarehouseService.INSTANCE
            .queryRecommendWarehouseList(BaseMapReq.newBuilder().create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserBean>>() {
                @Override
                public void onSuccess(List<PurchaserBean> result) {
                    mView.showList(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }
}
