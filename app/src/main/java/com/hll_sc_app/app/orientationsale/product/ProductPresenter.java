package com.hll_sc_app.app.orientationsale.product;


import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.user.CategoryResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ProductPresenter implements IProductContract.IProductPresenter {

    private IProductContract.IProductView mView;

    private Integer pageNum = 1;

    @Override
    public void queryCategory() {
        RegisterComplementPresenter.getCategoryObservable()
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CategoryResp>() {
                    @Override
                    public void onSuccess(CategoryResp resp) {
                        mView.showCategoryList(resp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryGoodsList(Integer pageNo, boolean showLoadIng) {
        if (pageNo != null && pageNo != 0) {
            this.pageNum = pageNo;
        }
        GoodsListReq req = new GoodsListReq();
        req.setPageNum(this.pageNum);
        req.setName(mView.getName());
        req.setGroupID(UserConfig.getGroupID());
        req.setCategorySubID(mView.getCategorySubId());
        req.setName(mView.getName());
        BaseReq<GoodsListReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.queryGoodsList(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoadIng) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<GoodsBean>>() {
                    @Override
                    public void onSuccess(List<GoodsBean> list) {
                        mView.showList(list, pageNum != 1);
                        pageNum += 1;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void register(IProductContract.IProductView view) {
        this.mView = view;
    }

    static ProductPresenter newInstance() {
        return new ProductPresenter();
    }

}
