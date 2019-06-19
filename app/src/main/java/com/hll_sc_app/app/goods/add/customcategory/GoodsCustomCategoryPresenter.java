package com.hll_sc_app.app.goods.add.customcategory;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.CustomCategorySortBean;
import com.hll_sc_app.bean.goods.CustomCategorySortReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 自定义分类
 *
 * @author zhuyingsong
 * @date 2019/6/18
 */
public class GoodsCustomCategoryPresenter implements GoodsCustomCategoryContract.IGoodsCustomCategoryPresenter {
    private GoodsCustomCategoryContract.IGoodsCustomCategoryView mView;

    static GoodsCustomCategoryPresenter newInstance() {
        return new GoodsCustomCategoryPresenter();
    }

    @Override
    public void start() {
        queryCustomCategory();
    }

    @Override
    public void register(GoodsCustomCategoryContract.IGoodsCustomCategoryView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCustomCategory() {
        getQueryCustomCategoryObservable().doOnSubscribe(disposable -> mView.showLoading())
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

    @Override
    public void editCustomCategory(CustomCategoryBean bean, String actionType) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", actionType)
            .put("categoryLevel", TextUtils.equals(bean.getCategoryLevel(), "3") ? "2" : "1")
            .put("categoryName", bean.getCategoryName())
            .put("groupID", UserConfig.getGroupID())
            .put("id", bean.getId())
            .put("shopCategoryPID", bean.getShopCategoryPID())
            .put("sort", bean.getSort())
            .create();
        GoodsService.INSTANCE.editCustomCategory(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .flatMap((Function<Object, ObservableSource<CustomCategoryResp>>) o -> getQueryCustomCategoryObservable())
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

    @Override
    public void sortCustomCategory(List<CustomCategorySortBean> list) {
        BaseReq<CustomCategorySortReq> baseReq = new BaseReq<>();
        CustomCategorySortReq req = new CustomCategorySortReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setCategorys(list);
        baseReq.setData(req);
        GoodsService.INSTANCE.sortCustomCategory(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .flatMap((Function<Object, ObservableSource<CustomCategoryResp>>) o -> getQueryCustomCategoryObservable())
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

    private Observable<CustomCategoryResp> getQueryCustomCategoryObservable() {
        BaseMapReq req = BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID()).create();
        return GoodsService.INSTANCE.queryCustomCategory(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }
}
