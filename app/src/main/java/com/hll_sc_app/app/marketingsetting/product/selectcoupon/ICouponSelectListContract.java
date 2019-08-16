package com.hll_sc_app.app.marketingsetting.product.selectcoupon;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.marketingsetting.CouponListBean;

import java.util.List;

public interface ICouponSelectListContract {
    interface IView extends ILoadView {
        void showList(List<CouponListBean> couponListBeans);
    }

    interface IPresenter extends com.hll_sc_app.base.IPresenter<IView> {
        void getCouponList();
    }
}
