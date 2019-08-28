package com.hll_sc_app.app.marketingsetting.coupon.check;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;

public interface IMarketingCouponCheckContract {
    interface IView extends ILoadView {

        void showDetail(MarketingDetailCheckResp resp);

        String getDiscountID();
    }

    interface IPresent extends IPresenter<IView> {
        void getDetail();
    }
}
