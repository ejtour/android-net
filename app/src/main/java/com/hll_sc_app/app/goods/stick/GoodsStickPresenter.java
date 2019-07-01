package com.hll_sc_app.app.goods.stick;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 商品置顶管理
 *
 * @author zhuyingsong
 * @date 2019/7/1
 */
public class GoodsStickPresenter implements GoodsStickContract.IGoodsStickPresenter {
    private GoodsStickContract.IGoodsStickView mView;

    static GoodsStickPresenter newInstance() {
        return new GoodsStickPresenter();
    }

    @Override
    public void start() {
        queryCustomCategory();
    }

    @Override
    public void register(GoodsStickContract.IGoodsStickView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCustomCategory() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("getResource", "0")
            .put("groupID", UserConfig.getGroupID())
            .create();
        GoodsService.INSTANCE.queryCustomCategory2Top(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CustomCategoryResp>() {
                @Override
                public void onSuccess(CustomCategoryResp resp) {
                    mView.showCustomCategoryList(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

}
