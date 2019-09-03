package com.hll_sc_app.app.marketingsetting.product;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.marketingsetting.MarketingListResp;
import com.hll_sc_app.bean.marketingsetting.MarketingStatusBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ProductMarketingPresenter implements IProductMarketingContract.IPresenter {
    private IProductMarketingContract.IView mView;

    private int pageNum = 1;
    private int pageNumTemp = 1;
    private int pageSize = 20;


    static public ProductMarketingPresenter newInstance() {
        return new ProductMarketingPresenter();
    }

    @Override
    public void start() {
        getMarketingStatus();
        getMarketingProductList(true);
    }

    @Override
    public void register(IProductMarketingContract.IView view) {
        mView = view;
    }

    @Override
    public void getMarketingStatus() {
        getMarketingStatusObservable()
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<MarketingStatusBean>>() {
                    @Override
                    public void onSuccess(List<MarketingStatusBean> marketingStatusBeans) {
                        mView.showMarketingStatus(marketingStatusBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    public static Observable<List<MarketingStatusBean>> getMarketingStatusObservable() {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder().put("name", "DiscountStatusEnum").create();
        return MarketingSettingService.INSTANCE
                .getMarketingStatus(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>());
    }

    @Override
    public void getMarketingProductList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("discountName", mView.getDiscountName())
                .put("discountStatus", mView.getDiscountStatus())
                .put("discountType", mView.getDiscountType())
                .put("groupID", userBean.getGroupID())
                .put("pageNum", String.valueOf(pageNumTemp))
                .put("pageSize", String.valueOf(pageSize))
                .put("startTime", mView.getStartTime())
                .put("endTime", mView.getEndTime())
                .create();

        MarketingSettingService.INSTANCE
                .getMarketingList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<MarketingListResp>() {
                    @Override
                    public void onSuccess(MarketingListResp marketingListResp) {
                        pageNum = pageNumTemp;
                        mView.showList(marketingListResp.getList());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageNumTemp = pageNum;
                        mView.showError(e);
                    }
                });

    }


    @Override
    public void getMoreList() {
        pageNumTemp++;
        getMarketingProductList(false);
    }

    @Override
    public void refreshList() {
        pageNumTemp = 0;
        getMarketingProductList(false);
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }
}
