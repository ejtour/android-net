package com.hll_sc_app.app.marketingsetting.coupon;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.app.marketingsetting.product.check.ProductMarketingCheckPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.ChangeMarketingStatusReq;
import com.hll_sc_app.bean.marketingsetting.CouponListReq;
import com.hll_sc_app.bean.marketingsetting.CouponListResp;

public class MarketingCouponPresent implements IMarketingCouponContract.IPresent {

    private IMarketingCouponContract.IView mView;
    private int pageNum = 1;
    private int tempNum = 1;
    private int pageSize = 20;

    public static MarketingCouponPresent newInstance() {
        return new MarketingCouponPresent();
    }

    @Override
    public void start() {
        getCouponList(true);
    }

    @Override
    public void register(IMarketingCouponContract.IView view) {
        mView = view;
    }

    @Override
    public void getCouponList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        CouponListReq req = new CouponListReq();
        req.setPageNum(tempNum);
        req.setPageSize(pageSize);
        req.setDiscountType(3);
        req.setDiscountStatus(0);
        req.setGroupID(userBean.getGroupID());
        getCouponListObsevable(req, new SimpleObserver<CouponListResp>(mView, isLoading) {
            @Override
            public void onSuccess(CouponListResp resp) {
                pageNum = tempNum;
                mView.getCouponListSuccess(resp, pageNum > 1);
            }

            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
                tempNum = pageNum;
            }
        });
    }

    public static void getCouponListObsevable(CouponListReq req, SimpleObserver<CouponListResp> observer) {
        BaseReq<CouponListReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .getCouponList(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }

    @Override
    public void freshList() {
        tempNum = 1;
        getCouponList(false);
    }

    @Override
    public void loadMore() {
        tempNum += 1;
        getCouponList(false);
    }

    @Override
    public void changeCouponStatus(String id, int statusValue) {
        ChangeMarketingStatusReq req = new ChangeMarketingStatusReq();
        req.setDiscountStatus(statusValue + "");
        req.setId(id);
        ProductMarketingCheckPresenter.changeMarketingStatus(req, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.changeStatusSuccess(statusValue);
            }
        });
    }
}
