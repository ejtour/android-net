package com.hll_sc_app.app.goods.template;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.goods.add.GoodsAddPresenter;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.GoodsTemplateResp;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 从商品库导入
 *
 * @author zhuyingsong
 * @date 2019/6/27
 */
public class GoodsTemplateListPresenter implements GoodsTemplateListContract.IGoodsTemplateListPresenter {
    private GoodsTemplateListContract.IGoodsTemplateListView mView;
    private int mPageNum;
    private int mTempPageNum;
    private CategoryResp mCategoryResp;
    private List<LabelBean> mLabelList;

    static GoodsTemplateListPresenter newInstance() {
        return new GoodsTemplateListPresenter();
    }

    @Override
    public void start() {
        queryGoodsTemplateList(true);
    }

    @Override
    public void register(GoodsTemplateListContract.IGoodsTemplateListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGoodsTemplateList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryGoodsTemplateList(showLoading);
    }

    @Override
    public void queryMoreGoodsTemplateList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryGoodsTemplateList(false);
    }

    @Override
    public void queryCategory() {
        if (mCategoryResp != null) {
            mView.showCategoryFilterWindow(mCategoryResp);
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
                    mView.showCategoryFilterWindow(mCategoryResp);
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
            mView.showLabelFilterWindow(mLabelList);
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
                    mView.showLabelFilterWindow(mLabelList);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryGoodsTemplateList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("selectSource", "shopmall")
            .put("productName", mView.getSearchContent())
            .put("brandName", mView.getBrandName())
            .put("productPlace", mView.getProductPlace())
            .put("labelIds", "")
            .put("categoryThreeIds", "")
            .create();
        GoodsService.INSTANCE.queryGoodsTemplateList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<GoodsTemplateResp>() {
                @Override
                public void onSuccess(GoodsTemplateResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showGoodsTemplateList(resp.getRecords(), mPageNum != 1, resp.getTotalSize());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
