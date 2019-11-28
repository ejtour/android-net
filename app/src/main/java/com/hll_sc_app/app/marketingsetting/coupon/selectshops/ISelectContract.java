package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;

public interface ISelectContract {

    interface ISearchGroupsView extends ILoadView {
        void showGroups(CooperationPurchaserResp resp);

        String getSearchText();
    }

    interface ISearchGroupsPresent extends IPresenter<ISearchGroupsView> {
        void getGroups();

    }


    interface ISearchShopsView extends ILoadView {
        String getPurchaserID();
        String getSearchText();
        void showShops(CooperationShopListResp resp);
    }

    interface ISearchShopsPresent extends IPresenter<ISearchShopsView> {
        void getShops();

    }
}
