package com.hll_sc_app.app.warehouse.add;

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
 * 代仓管理-搜索新增
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public class WarehouseAddPresenter implements WarehouseAddContract.IWarehouseAddPresenter {
    private WarehouseAddContract.IWarehouseAddView mView;
    private int mPageNum;
    private int mTempPageNum;

    static WarehouseAddPresenter newInstance() {
        return new WarehouseAddPresenter();
    }

    @Override
    public void register(WarehouseAddContract.IWarehouseAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserList(showLoading);
    }

    @Override
    public void queryPurchaserList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserList(false);
    }

    private void toQueryPurchaserList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "warehouse")
            .put("pageNo", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("searchParam", mView.getSearchParam())
            .put("groupType", "-1")
            .create();
        WarehouseService.INSTANCE
            .queryPurchaserList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserBean>>() {
                @Override
                public void onSuccess(List<PurchaserBean> list) {
                    mPageNum = mTempPageNum;
                    mView.showPurchaserList(list, mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
