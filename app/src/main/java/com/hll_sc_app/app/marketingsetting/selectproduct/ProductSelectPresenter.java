package com.hll_sc_app.app.marketingsetting.selectproduct;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.contractmanage.add.ContractManageAddActivity;
import com.hll_sc_app.app.marketingsetting.product.add.ProductMarketingAddActivity;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.goods.SkuProductsResp;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
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
        if (TextUtils.equals(mView.getActivityName(), ProductMarketingAddActivity.class.getSimpleName())) {
            toQueryMarketingGoodsList("discount_sets",showLoading);
        }else if(TextUtils.equals(mView.getActivityName(), ContractManageAddActivity.class.getSimpleName())){
            toQueryMarketingGoodsList("",showLoading);
        }
    }

    private void toQueryMarketingGoodsList(String actionType,boolean showLoading) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("actionType", actionType)
                .put("categorySubID", mView.getCategorySubId())
                .put("name", mView.getName())
                .put("groupID", UserConfig.getGroupID())
                .put("pageNum", String.valueOf(mTempPageNum))
                .put("pageSize", "20").create();

        GoodsService.INSTANCE.querySkuProducts(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<SkuProductsResp>() {
                    @Override
                    public void onSuccess(SkuProductsResp skuProductsResp) {
                        mPageNum = mTempPageNum;
                        mView.showList(skuProductsResp.getRecords(), mPageNum != 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


    private void processData(List<GoodsBean> list) {
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
    }
}
