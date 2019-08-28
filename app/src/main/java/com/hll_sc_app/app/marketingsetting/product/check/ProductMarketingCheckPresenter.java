package com.hll_sc_app.app.marketingsetting.product.check;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.ChangeMarketingStatusReq;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckReq;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;

public class ProductMarketingCheckPresenter implements IProductMarketingCheckContract.IPresenter {

    private IProductMarketingCheckContract.IView mView;

    public static ProductMarketingCheckPresenter newInstance() {
        return new ProductMarketingCheckPresenter();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(IProductMarketingCheckContract.IView view) {
        mView = view;
    }

    @Override
    public void getMarketingDetail() {
        MarketingDetailCheckReq req = new MarketingDetailCheckReq();
        req.setActionType("");
        req.setDiscountID(mView.getDiscountId());
        marketingCheckDetail(req, new SimpleObserver<MarketingDetailCheckResp>(mView) {
            @Override
            public void onSuccess(MarketingDetailCheckResp resp) {
                mView.showDetai(resp);
            }
        });

    }

    /**
     * 查询数据
     *
     * @return
     */
    public static void marketingCheckDetail(MarketingDetailCheckReq req, SimpleObserver<MarketingDetailCheckResp> observer) {
        BaseReq<MarketingDetailCheckReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .getMarketingDetail(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);

    }

    @Override
    public void changeMarketingStatus() {
        ChangeMarketingStatusReq req = new ChangeMarketingStatusReq();
        req.setDiscountStatus("4");
        req.setId(mView.getDiscountId());
        changeMarketingStatus(req, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.changeMarketingStatusSuccess();
            }
        });

    }

    /**
     * 查询数据
     *
     * @return
     */
    static  public void changeMarketingStatus(ChangeMarketingStatusReq req, SimpleObserver<Object> observer) {
        BaseReq<ChangeMarketingStatusReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .changeMarketingStatus(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);

    }
}
