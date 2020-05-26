package com.hll_sc_app.app.stockmanage.selectproduct;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 *
 */
public class ProductSelectPresenter implements IProductSelectContract.IGoodsStickPresenter {
    private IProductSelectContract.IGoodsStickView mView;
    private int mPageNum;
    private int mTempPageNum;

    static ProductSelectPresenter newInstance() {
        return new ProductSelectPresenter();
    }

    @Override
    public void start() {
        queryCategory();
    }

    @Override
    public void register(IProductSelectContract.IGoodsStickView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCategory() {
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
        toQueryMarketingGoodsList(showLoading);
    }

    @Override
    public void queryMoreGoodsList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryMarketingGoodsList(false);
    }

    private void toQueryMarketingGoodsList(boolean showLoading) {
        GoodsListReq goodsListReq = new GoodsListReq();
        goodsListReq.setPageSize(20);
        goodsListReq.setPageNum(mTempPageNum);
        goodsListReq.setActionType(mView.getActionType());
        goodsListReq.setShopProductCategorySubID(mView.getCategorySubID());
        goodsListReq.setShopProductCategoryThreeID(mView.getCategoryThreeID());
        goodsListReq.setGroupID(UserConfig.getGroupID());
        goodsListReq.setName(mView.getName());
        BaseReq<GoodsListReq> baseReq = new BaseReq<>();
        baseReq.setData(goodsListReq);
        GoodsService.INSTANCE.queryGoodsList(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<GoodsBean>>() {
                    @Override
                    public void onSuccess(List<GoodsBean> goodsBeans) {
                        mView.showList(goodsBeans, mTempPageNum > 1);
                        mPageNum = mTempPageNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        mTempPageNum = mPageNum;
                    }
                });
    }


  /*  private void processData(List<GoodsBean> list) {
        ArrayList<SkuGoodsBean> goodsList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (GoodsBean bean : list) {
                List<SpecsBean> specsBeans = bean.getSpecs();
                for (SpecsBean specsBean : specsBeans) {
                    SkuGoodsBean skuGoodsBean = new SkuGoodsBean();
                    skuGoodsBean.setSpecContent(specsBean.getSpecContent());
                    skuGoodsBean.setShopProductCategoryThreeID(bean.getShopProductCategoryThreeID());
                    skuGoodsBean.setSpecID(specsBean.getProductSpecID());
                    skuGoodsBean.setCostPrice(specsBean.getCostPrice());
                    skuGoodsBean.setImgUrl(bean.getImgUrl());
                    skuGoodsBean.setProductCode(bean.getProductCode());
                    skuGoodsBean.setProductID(bean.getProductID());
                    skuGoodsBean.setProductName(bean.getProductName());
                    skuGoodsBean.setProductPrice(specsBean.getProductPrice());
                    skuGoodsBean.setSaleUnitName(specsBean.getSaleUnitName());
                    skuGoodsBean.setCategoryThreeID(bean.getCategoryID());
                    skuGoodsBean.setCategorySubID(bean.getCategorySubID());
                    goodsList.add(skuGoodsBean);
                }
            }
        }
        mView.showList(goodsList, mPageNum != 1);
    }*/
}
