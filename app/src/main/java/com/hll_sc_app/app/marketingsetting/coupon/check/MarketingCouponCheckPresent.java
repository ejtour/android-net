package com.hll_sc_app.app.marketingsetting.coupon.check;

import com.hll_sc_app.app.marketingsetting.product.check.ProductMarketingCheckPresenter;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckReq;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;

public class MarketingCouponCheckPresent implements IMarketingCouponCheckContract.IPresent {
    private IMarketingCouponCheckContract.IView mView;

    static public MarketingCouponCheckPresent newInstance() {
        return new MarketingCouponCheckPresent();
    }

    @Override
    public void register(IMarketingCouponCheckContract.IView view) {
        mView = view;
    }

    @Override
    public void getDetail() {
        MarketingDetailCheckReq req = new MarketingDetailCheckReq();
        req.setDiscountID(mView.getDiscountID());
        req.setActionType("1");
        ProductMarketingCheckPresenter.marketingCheckDetail(req, new SimpleObserver<MarketingDetailCheckResp>(mView) {
            @Override
            public void onSuccess(MarketingDetailCheckResp resp) {
                mView.showDetail(resp);
            }
        });
    }
}
