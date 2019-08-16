package com.hll_sc_app.app.marketingsetting.product.selectcoupon;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.CouponListBean;
import com.hll_sc_app.bean.marketingsetting.CouponListReq;

import java.util.List;


public class CouponSelectListPresenter implements ICouponSelectListContract.IPresenter {
    private ICouponSelectListContract.IView mView;

    public static CouponSelectListPresenter newInstance() {
        return new CouponSelectListPresenter();
    }

    @Override
    public void register(ICouponSelectListContract.IView view) {
        mView = view;
    }

    @Override
    public void getCouponList() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        CouponListReq req = new CouponListReq();
        req.setGroupID(userBean.getGroupID());
        req.setDiscountType(3);
        getCounponListObservable(req, new SimpleObserver<List<CouponListBean>>(mView) {
            @Override
            public void onSuccess(List<CouponListBean> couponListBeans) {
                mView.showList(couponListBeans);
            }
        });

    }

    public static void getCounponListObservable(CouponListReq req, SimpleObserver<List<CouponListBean>> observer) {
        BaseReq<CouponListReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .getCounponList(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }
}
