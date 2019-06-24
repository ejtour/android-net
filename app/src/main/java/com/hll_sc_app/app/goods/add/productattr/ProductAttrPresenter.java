package com.hll_sc_app.app.goods.add.productattr;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择商品属性
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
public class ProductAttrPresenter implements ProductAttrContract.IProductAttrPresenter {
    private ProductAttrContract.IProductAttr mView;

    static ProductAttrPresenter newInstance() {
        return new ProductAttrPresenter();
    }

    @Override
    public void start() {
        queryProductAttrsList();
    }

    @Override
    public void register(ProductAttrContract.IProductAttr view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryProductAttrsList() {
        BaseMapReq req = BaseMapReq.newBuilder().create();
        GoodsService.INSTANCE.queryProductAttrsList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ArrayList<ProductAttrBean>>() {
                @Override
                public void onSuccess(ArrayList<ProductAttrBean> list) {
                    mView.showProductAttrList(list);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
