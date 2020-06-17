package com.hll_sc_app.app.agreementprice.quotation.add.goods;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 报价单-新增商品
 *
 * @author zhuyingsong
 * @date 2019/7/10
 */
public class GoodsQuotationSelectPresenter implements GoodsQuotationSelectContract.IGoodsStickPresenter {
    private GoodsQuotationSelectContract.IGoodsStickView mView;
    private int mPageNum;
    private int mTempPageNum;

    static GoodsQuotationSelectPresenter newInstance() {
        return new GoodsQuotationSelectPresenter();
    }

    @Override
    public void start() {
        queryCategory();
    }

    @Override
    public void register(GoodsQuotationSelectContract.IGoodsStickView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCategory() {
        if (GlobalPreference.getParam(Constants.ONLY_RECEIVE, false)) {
            getSupplyChainCategory(mView.getExtGroupID())
                    .doOnSubscribe(disposable -> mView.showLoading())
                    .doFinally(() -> mView.hideLoading())
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                    .subscribe(new BaseCallback<SingleListResp<CategoryItem>>() {
                        @Override
                        public void onSuccess(SingleListResp<CategoryItem> categoryItemSingleListResp) {
                            CategoryResp resp = new CategoryResp();
                            resp.setList2(categoryItemSingleListResp.getRecords());
                            mView.showCategoryList(resp);
                        }

                        @Override
                        public void onFailure(UseCaseException e) {
                            mView.showError(e);
                        }
                    });
        } else {
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
    }

    public static Observable<SingleListResp<CategoryItem>> getSupplyChainCategory(String extGroupID) {
        return GoodsService.INSTANCE
                .getSupplyChainCategory(BaseMapReq.newBuilder()
                        .put("groupID", extGroupID)
                        .put("shopSupplierID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>());
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
        if (GlobalPreference.getParam(Constants.ONLY_RECEIVE, false)) {
            GoodsService.INSTANCE.searchSupplyChainGoods(BaseMapReq.newBuilder()
                    .put("categoryID", mView.getCategorySubId())
                    .put("groupID", mView.getExtGroupID())
                    .put("pageNo", String.valueOf(mTempPageNum))
                    .put("pageSize", "20")
                    .put("searchKey", mView.getName())
                    .put("shopSupplierID", UserConfig.getGroupID())
                    .create())
                    .compose(ApiScheduler.getObservableScheduler())
                    .map(new Precondition<>())
                    .doOnSubscribe(disposable -> {
                        if (showLoading) {
                            mView.showLoading();
                        }
                    })
                    .doFinally(() -> mView.hideLoading())
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                    .subscribe(new BaseCallback<SingleListResp<GoodsBean>>() {
                        @Override
                        public void onSuccess(SingleListResp<GoodsBean> goodsBeanSingleListResp) {
                            mPageNum = mTempPageNum;
                            processData(goodsBeanSingleListResp.getRecords());
                        }

                        @Override
                        public void onFailure(UseCaseException e) {
                            mView.showError(e);
                        }
                    });
        } else {
            GoodsListReq req = new GoodsListReq();
            req.setActionType("priceBill");
            req.setCategorySubID(mView.getCategorySubId());
            req.setIsWareHourse(TextUtils.isEmpty(mView.getCargoOwnId()) ? "0" : "1");
            req.setCargoOwnerID(mView.getCargoOwnId());
            req.setName(mView.getName());
            req.setGroupID(UserConfig.getGroupID());
            req.setPageNum(mTempPageNum);
            req.setPageSize(20);
            GoodsService.INSTANCE.queryGoodsList(new BaseReq<>(req))
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
                        public void onSuccess(List<GoodsBean> resp) {
                            mPageNum = mTempPageNum;
                            processData(resp);
                        }

                        @Override
                        public void onFailure(UseCaseException e) {
                            mView.showError(e);
                        }
                    });
        }
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
                    skuGoodsBean.setSpecID(TextUtils.isEmpty(specsBean.getProductSpecID()) ? bean.getProductID() : specsBean.getProductSpecID());
                    skuGoodsBean.setCostPrice(specsBean.getCostPrice());
                    skuGoodsBean.setImgUrl(bean.getImgUrl());
                    skuGoodsBean.setProductCode(bean.getProductCode());
                    skuGoodsBean.setProductID(bean.getProductID());
                    skuGoodsBean.setProductName(bean.getProductName());
                    skuGoodsBean.setProductPrice(TextUtils.isEmpty(specsBean.getProductPrice()) ? "0" : specsBean.getProductPrice());
                    skuGoodsBean.setSaleUnitName(TextUtils.isEmpty(specsBean.getSaleUnitName()) ? bean.getSaleUnitName() : specsBean.getSaleUnitName());
                    skuGoodsBean.setCategoryThreeID(bean.getCategoryID());
                    skuGoodsBean.setCategorySubID(bean.getCategorySubID());
                    goodsList.add(skuGoodsBean);
                }
            }
        }
        mView.showList(goodsList, mPageNum != 1);
    }
}
