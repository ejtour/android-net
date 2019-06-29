package com.hll_sc_app.app.goods.template.edit;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.GoodsAddBatchReq;
import com.hll_sc_app.bean.goods.GoodsAddBatchResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 从商品库导入-商品编辑
 *
 * @author zhuyingsong
 * @date 2019/6/29
 */
public class GoodsTemplateEditPresenter implements GoodsTemplateEditContract.IGoodsTemplateEditPresenter {
    private GoodsTemplateEditContract.IGoodsTemplateEditView mView;

    static GoodsTemplateEditPresenter newInstance() {
        return new GoodsTemplateEditPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(GoodsTemplateEditContract.IGoodsTemplateEditView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void batchAddGoods(GoodsAddBatchReq req) {
        BaseReq<GoodsAddBatchReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.addProductBatch(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<GoodsAddBatchResp>() {
                @Override
                public void onSuccess(GoodsAddBatchResp resp) {
                    mView.addSuccess(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
