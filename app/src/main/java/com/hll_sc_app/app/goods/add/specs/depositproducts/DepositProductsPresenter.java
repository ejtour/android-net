package com.hll_sc_app.app.goods.add.specs.depositproducts;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.SkuProductsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择押金商品列表
 *
 * @author zhuyingsong
 * @date 2019/6/20
 */
public class DepositProductsPresenter implements DepositProductsContract.ISaleUnitNameAddPresenter {
    private DepositProductsContract.ISaleUnitNameAddView mView;
    private int mPageNum;
    private int mTempPageNum;

    static DepositProductsPresenter newInstance() {
        return new DepositProductsPresenter();
    }

    @Override
    public void start() {
        queryDepositProducts(true);
    }

    @Override
    public void register(DepositProductsContract.ISaleUnitNameAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDepositProducts(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryDepositProducts(showLoading);
    }

    @Override
    public void queryMoreDepositProducts() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryDepositProducts(false);
    }

    private void toQueryDepositProducts(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "depositProduct")
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .create();
        GoodsService.INSTANCE.querySkuProducts(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<SkuProductsResp>() {
                @Override
                public void onSuccess(SkuProductsResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showDepositProductsList(resp.getRecords(), mPageNum != 1, resp.getTotal());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
