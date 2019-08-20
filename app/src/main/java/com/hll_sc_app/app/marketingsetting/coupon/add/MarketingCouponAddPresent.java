package com.hll_sc_app.app.marketingsetting.coupon.add;

import com.hll_sc_app.app.marketingsetting.product.add.ProductMarketingAddPresenter;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddReq;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddResp;

public class MarketingCouponAddPresent implements IMarketingCouponAddContract.IPresent {
    private IMarketingCouponAddContract.IView mView;

    static public MarketingCouponAddPresent newInstance() {
        return new MarketingCouponAddPresent();
    }

    @Override
    public void register(IMarketingCouponAddContract.IView view) {
        mView = view;
    }


    @Override
    public void save() {
        /* areaScope: 1,
        couponType: 1,
        customerScope: 1,
        discountRuleType: 2,
        discountStage: 0,
        discountType: 3,*/
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        MarketingProductAddReq req = new MarketingProductAddReq();
        req.setAreaScope(1);
        req.setCouponType(1);
        req.setCustomerScope(1);
        req.setDiscountRuleType(2);
        req.setDiscountStage(0);
        req.setDiscountType(3);
        req.setDiscountName(mView.getCouponName());
        req.setRuleList(mView.getRuleList());
        req.setValidityType(mView.getValidityType());
        req.setValidityDays(mView.getValidityDays());
        req.setCouponCondition(mView.getCouponCondition());
        req.setGroupID(userBean.getGroupID());
        req.setDiscountStartTime(mView.getDiscountStartTime());
        req.setDiscountEndTime(mView.getDiscountEndTime());

        ProductMarketingAddPresenter.productAddRespObservable(req, new SimpleObserver<MarketingProductAddResp>(mView) {
            @Override
            public void onSuccess(MarketingProductAddResp resp) {
                mView.addSuccess();
            }
        });
    }
}
