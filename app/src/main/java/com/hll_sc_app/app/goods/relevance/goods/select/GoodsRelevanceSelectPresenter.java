package com.hll_sc_app.app.goods.relevance.goods.select;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.DepositProductsResp;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 第三方商品关联-选择关联商品
 *
 * @author zhuyingsong
 * @date 2019/7/5
 */
public class GoodsRelevanceSelectPresenter implements GoodsRelevanceSelectContract.IGoodsStickPresenter {
    private GoodsRelevanceSelectContract.IGoodsStickView mView;
    private int mPageNum;
    private int mTempPageNum;

    static GoodsRelevanceSelectPresenter newInstance() {
        return new GoodsRelevanceSelectPresenter();
    }

    @Override
    public void start() {
        queryCategory();
    }

    @Override
    public void register(GoodsRelevanceSelectContract.IGoodsStickView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

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
    public void queryGoodsList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryGoodsList(showLoading);
    }

    @Override
    public void queryMoreGoodsList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryGoodsList(false);
    }

    private void toQueryGoodsList(boolean showLoading) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20");
        if (TextUtils.isEmpty(mView.getCategorySubId())) {
            // 推荐
            builder.put("name", mView.getProductName())
                .put("productCode", mView.getProductCode())
                .put("actionType", "relateRecommend");
        } else {
            builder.put("name", mView.getName())
                .put("categorySubID", mView.getCategorySubId());
        }
        GoodsService.INSTANCE.queryDepositProducts(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<DepositProductsResp>() {
                @Override
                public void onSuccess(DepositProductsResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showList(resp.getRecords(), mPageNum != 1, resp.getTotal());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
