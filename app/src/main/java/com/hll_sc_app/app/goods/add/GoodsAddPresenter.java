package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
public class GoodsAddPresenter implements GoodsAddContract.IGoodsAddPresenter {
    private GoodsAddContract.IGoodsAddView mView;
    private CategoryResp mCategoryResp;
    private List<LabelBean> mLabelList;
    private ArrayList<ProductAttrBean> mProductAttrsList;

    static GoodsAddPresenter newInstance() {
        return new GoodsAddPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsAddContract.IGoodsAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void uploadImg(String path) {
        Upload.upload(mView, path, filepath -> mView.uploadSuccess(filepath));
    }

    @Override
    public void queryCategory() {
        if (mCategoryResp != null) {
            mView.showCategorySelectWindow(mCategoryResp);
            return;
        }
        RegisterComplementPresenter.getCategoryObservable()
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CategoryResp>() {
                @Override
                public void onSuccess(CategoryResp resp) {
                    mCategoryResp = resp;
                    mView.showCategorySelectWindow(mCategoryResp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void copyToCustomCategory(CategoryItem categoryItem1, CategoryItem categoryItem2,
                                     CategoryItem categoryItem3) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("categoryID", categoryItem1.getCategoryID())
            .put("categoryName", categoryItem1.getCategoryName())
            .put("categorySubID", categoryItem2.getCategoryID())
            .put("categorySubName", categoryItem2.getCategoryName())
            .put("categoryThreeID", categoryItem3.getCategoryID())
            .put("categoryThreeName", categoryItem3.getCategoryName())
            .put("groupID", UserConfig.getGroupID())
            .create();
        GoodsService.INSTANCE.copyToCustomCategory(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CopyCategoryBean>() {
                @Override
                public void onSuccess(CopyCategoryBean resp) {
                    mView.showCustomCategory(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryLabelList() {
        if (!CommonUtils.isEmpty(mLabelList)) {
            mView.showLabelSelectWindow(mLabelList);
            return;
        }
        GoodsAddPresenter.getQueryLabelListObservable()
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<LabelBean>>() {
                @Override
                public void onSuccess(List<LabelBean> list) {
                    mLabelList = list;
                    mView.showLabelSelectWindow(mLabelList);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    public static Observable<List<LabelBean>> getQueryLabelListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder().create();
        return GoodsService.INSTANCE.queryLabelList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    @Override
    public void addProduct(GoodsBean bean) {
        if (bean == null) {
            return;
        }
        bean.setAddResource("1");
        BaseReq<GoodsBean> baseReq = new BaseReq<>();
        baseReq.setData(bean);
        GoodsService.INSTANCE.addProduct(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.addSuccess(false);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editProduct(GoodsBean bean) {
        if (bean == null) {
            return;
        }
        bean.setUpdateResource("1");
        BaseReq<GoodsBean> baseReq = new BaseReq<>();
        baseReq.setData(bean);
        GoodsService.INSTANCE.editProduct(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.addSuccess(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
