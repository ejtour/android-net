package com.hll_sc_app.app.goods.add.productattr.brand.add;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.ProductBrandResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 商品品牌-新增
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
public class ProductBrandAddPresenter implements ProductBrandAddContract.ISaleUnitNameAddPresenter {
    private ProductBrandAddContract.ISaleUnitNameAddView mView;
    private int mPageNum;
    private int mTempPageNum;

    static ProductBrandAddPresenter newInstance() {
        return new ProductBrandAddPresenter();
    }

    @Override
    public void start() {
        queryProductBrandList(true);
    }

    @Override
    public void register(ProductBrandAddContract.ISaleUnitNameAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryProductBrandList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryProductBrandList(showLoading);
    }

    @Override
    public void queryMoreProductBrandList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryProductBrandList(false);
    }

    @Override
    public void delProductBrandReq(String id) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("id", id)
            .create();
        GoodsService.INSTANCE.delProductBrandReq(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("删除品牌申请成功");
                    queryProductBrandList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void addProductBrand(String brandName) {
        if (TextUtils.isEmpty(brandName)) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("brandName", brandName)
            .create();
        GoodsService.INSTANCE.addProductBrand(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("提交品牌申请成功");
                    mView.clearEditText();
                    queryProductBrandList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryProductBrandList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("status", "0")
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .create();
        GoodsService.INSTANCE.queryAllProductBrandList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ProductBrandResp>() {
                @Override
                public void onSuccess(ProductBrandResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showProductBrandList(resp.getRecords(), mPageNum != 1, resp.getTotalSize());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
