package com.hll_sc_app.app.goods.add.specs;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.SkuCheckResp;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 新增商品规格
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public class GoodsSpecsAddPresenter implements GoodsSpecsAddContract.IGoodsAddPresenter {
    private GoodsSpecsAddContract.IGoodsAddView mView;
    private CategoryResp mCategoryResp;

    static GoodsSpecsAddPresenter newInstance() {
        return new GoodsSpecsAddPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsSpecsAddContract.IGoodsAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void checkSkuCode(String skuCode) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("skuCode", skuCode)
            .create();
        GoodsService.INSTANCE.checkSkuCode(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<SkuCheckResp>() {
                @Override
                public void onSuccess(SkuCheckResp resp) {
                    if (resp.isFlag()) {
                        mView.checkSuccess();
                    } else {
                        mView.showToast(resp.getMessage());
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });

    }
}
