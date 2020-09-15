package com.hll_sc_app.app.marketingsetting.product.goodslist;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.MarketingProductDelReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/10
 */
public class GoodsListPresenter implements IGoodsListContract.IGoodsListPresenter {
    private IGoodsListContract.IGoodsListView mView;

    private GoodsListPresenter() {
    }

    public static GoodsListPresenter newInstance() {
        return new GoodsListPresenter();
    }

    @Override
    public void del(String discountID, String productID) {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        };
        MarketingSettingService.INSTANCE
                .delMarketingGoods(new BaseReq<>(new MarketingProductDelReq(discountID, productID)))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IGoodsListContract.IGoodsListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
