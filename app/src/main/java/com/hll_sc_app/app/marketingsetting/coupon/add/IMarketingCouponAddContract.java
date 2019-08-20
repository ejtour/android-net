package com.hll_sc_app.app.marketingsetting.coupon.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;

import java.util.List;

public interface IMarketingCouponAddContract {
    interface IView extends ILoadView {

        void addSuccess();

        String getCouponName();

        List<RuleListBean> getRuleList();


        int getValidityType();

        //0无限制1满减
        int getCouponCondition();

        String getValidityDays();

        String getDiscountStartTime();

        String getDiscountEndTime();
    }

    interface IPresent extends IPresenter<IView> {
        void save();
    }
}
