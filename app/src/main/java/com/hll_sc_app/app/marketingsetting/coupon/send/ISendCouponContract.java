package com.hll_sc_app.app.marketingsetting.coupon.send;


import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;

import java.util.List;

public interface ISendCouponContract {
    interface IView extends ILoadView {
        String getNode();

        String getCouponSendNumber();

        List<CouponSendReq.GroupandShopsBean> getCustomers();

        void sendSuccess(String message);
    }

    interface IPresent extends IPresenter<IView> {
        void sendCoupon();
    }
}
