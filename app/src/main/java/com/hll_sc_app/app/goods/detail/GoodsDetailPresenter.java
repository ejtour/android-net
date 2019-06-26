package com.hll_sc_app.app.goods.detail;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.goods.list.GoodsListFragmentPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 商品详情
 *
 * @author zhuyingsong
 * @date 2019/6/13
 */
public class GoodsDetailPresenter implements GoodsDetailContract.IGoodsDetailPresenter {
    private GoodsDetailContract.IGoodsDetailView mView;

    static GoodsDetailPresenter newInstance() {
        return new GoodsDetailPresenter();
    }

    @Override
    public void start() {
        queryGoodsDetail();
    }

    @Override
    public void register(GoodsDetailContract.IGoodsDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGoodsDetail() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("productID", mView.getProductId())
            .put("forward", "1")
            .create();
        GoodsService.INSTANCE.queryGoodsDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<GoodsBean>() {
                @Override
                public void onSuccess(GoodsBean resp) {
                    mView.showDetail(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void updateSpecStatus(List<SpecsBean> list) {
        if (CommonUtils.isEmpty(list)) {
            return;
        }
        GoodsListFragmentPresenter.getUpdateSpecStatusObservable(list)
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("修改成功");
                    queryGoodsDetail();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
