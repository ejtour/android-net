package com.hll_sc_app.app.marketingsetting.coupon.usedetail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.marketingsetting.CouponUseDetailListResp;

public interface IUseDetailContract {
    interface IView extends ILoadView {
        void showDetail(CouponUseDetailListResp resp,boolean isMore);

        String getDiscountID();

        int getCouponStatus();


    }

    interface IPresent extends IPresenter<IView> {
        void getMarketingDetail(boolean isShowLoading);

        void refresh();

        void getMore();
    }
}
