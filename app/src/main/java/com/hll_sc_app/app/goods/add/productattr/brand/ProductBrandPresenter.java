package com.hll_sc_app.app.goods.add.productattr.brand;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 商品品牌
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
public class ProductBrandPresenter implements ProductBrandContract.IProductAttrBrandPresenter {
    private ProductBrandContract.IProductAttrBrand mView;

    static ProductBrandPresenter newInstance() {
        return new ProductBrandPresenter();
    }

    @Override
    public void start() {
        queryProductBrandList("");
    }

    @Override
    public void register(ProductBrandContract.IProductAttrBrand view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryProductBrandList(String brandName) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("brandName", brandName)
            .create();
        GoodsService.INSTANCE.queryProductBrandList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<String>>() {
                @Override
                public void onSuccess(List<String> list) {
                    mView.showProductBrandList(list);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
