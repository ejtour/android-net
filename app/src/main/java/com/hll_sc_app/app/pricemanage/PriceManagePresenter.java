package com.hll_sc_app.app.pricemanage;

import android.app.Activity;
import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.PriceManageService;
import com.hll_sc_app.api.PriceRatioTemplateService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.goods.SkuProductsResp;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 售价设置
 *
 * @author zhuyingsong
 * @date 2019/7/12
 */
public class PriceManagePresenter implements PriceManageContract.IPriceManagePresenter {
    private PriceManageContract.IPriceManageView mView;
    private int mPageNum;
    private int mTempPageNum;
    private List<RatioTemplateBean> mListRation;
    private CustomCategoryResp mCategoryResp;

    static PriceManagePresenter newInstance() {
        return new PriceManagePresenter();
    }

    @Override
    public void start() {
        queryPriceManageList(true);
    }

    @Override
    public void register(PriceManageContract.IPriceManageView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPriceManageList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryDepositProducts(showLoading);
    }

    @Override
    public void queryMorePriceManageList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryDepositProducts(false);
    }

    @Override
    public void updateProductPrice(SkuGoodsBean bean, String productPrice) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("productID", bean.getProductID())
                .put("productPrice", productPrice)
                .put("specID", bean.getSpecID())
                .create();
        PriceManageService.INSTANCE.updateProductPrice(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        queryPriceManageList(true);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void updateCostPrice(SkuGoodsBean bean, String costPrice) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("productID", bean.getProductID())
                .put("costPrice", costPrice)
                .put("specID", bean.getSpecID())
                .create();
        PriceManageService.INSTANCE.updateCostPrice(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        queryPriceManageList(true);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryRatioTemplateList() {
        if (!CommonUtils.isEmpty(mListRation)) {
            mView.showRatioTemplateWindow(mListRation);
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("pageNum", "1")
                .put("pageSize", "0")
                .put("templateType", "2")
                .create();
        PriceRatioTemplateService.INSTANCE.queryRatioTemplateList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<RatioTemplateResp>() {
                    @Override
                    public void onSuccess(RatioTemplateResp resp) {
                        mListRation = resp.getRecords();
                        mView.showRatioTemplateWindow(mListRation);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryCustomCategory() {
        if (mCategoryResp != null) {
            mView.showCustomCategoryWindow(mCategoryResp);
            return;
        }
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
                        mCategoryResp = resp;
                        mView.showCustomCategoryWindow(resp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void export(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setActionType("2");
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("sell_price");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.SellPrice sellPrice = new ExportReq.ParamsBean.SellPrice();
        sellPrice.setGroupID(userBean.getGroupID());
        if (mView.getIsWareHourse() != -1) {
            sellPrice.setIsWareHourse(mView.getIsWareHourse() + "");
        }
        sellPrice.setName(mView.getSearchParam());
        sellPrice.setProductStatus(mView.getProductStatus());
        sellPrice.setShopProductCategoryThreeIds(mView.getProductCategoryIds());
        paramsBean.setSellPrice(sellPrice);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ExportResp>() {
                    @Override
                    public void onSuccess(ExportResp resp) {
                        if (!TextUtils.isEmpty(email)) {
                            Utils.exportSuccess((Activity) mView, email);
                        } else {
                            Utils.exportFailure((Activity) mView, "噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (TextUtils.equals("00120112037", e.getCode())) {
                            Utils.bindEmail((Activity) mView, email -> {
                                export(email);
                            });
                        } else if (TextUtils.equals("00120112038", e.getCode())) {
                            Utils.exportFailure((Activity) mView, "当前没有可导出的数据");
                        } else {
                            Utils.exportFailure((Activity) mView, "噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                        }
                    }
                });
    }

    private void toQueryDepositProducts(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "sellPrice")
                .put("pageNum", String.valueOf(mTempPageNum))
                .put("pageSize", "20")
                .put("name", mView.getSearchParam())
                .put("productStatus", mView.getProductStatus())
                .put("isWareHourse", mView.getIsWareHourse() == -1 ? null : (mView.getIsWareHourse() + ""))
                .put("shopProductCategoryThreeIds", mView.getProductCategoryIds())
                .create();
        GoodsService.INSTANCE.querySkuProducts(req)
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
                    public void onSuccess(SkuProductsResp resp) {
                        mPageNum = mTempPageNum;
                        mView.showPriceManageList(resp.getRecords(), mPageNum != 1, resp.getTotal());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
