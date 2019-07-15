package com.hll_sc_app.app.pricemanage;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.PriceManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.RatioTemplateBean;
import com.hll_sc_app.bean.agreementprice.quotation.RatioTemplateResp;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.goods.SkuProductsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
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
        AgreementPriceService.INSTANCE.queryRatioTemplateList(req)
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

    private void toQueryDepositProducts(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "sellPrice")
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("name", mView.getSearchParam())
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
