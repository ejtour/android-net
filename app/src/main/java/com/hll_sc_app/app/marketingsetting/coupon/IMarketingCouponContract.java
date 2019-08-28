package com.hll_sc_app.app.marketingsetting.coupon;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.marketingsetting.CouponListResp;

public interface IMarketingCouponContract {
    interface IView extends ILoadView {

        void getCouponListSuccess(CouponListResp resp,boolean isMore);


        void changeStatusSuccess(int status);
    }

    interface IPresent extends IPresenter<IView> {
        void getCouponList(boolean isLoading);

        void freshList();

        void loadMore();

        void changeCouponStatus(String id,int statusValue);
    }


}
