package com.hll_sc_app.app.staffmanage.linkshop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;

import java.util.List;

/**
 * 员工列表——关联门店
 */
public interface StaffLinkShopListContract {

    interface IView extends ILoadView {
        void dropSuccess();

        void showShops(CooperationShopListResp resp, boolean isMore);

        String getSalesmanID();

        List<String> getShopIds();

        void editSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void queryCooperationShop(boolean isLoading);

        void refresh();

        void getMore();

        int getPageSize();

        void dropStaffEmployee();

        void editShopEmployee(ShopSettlementReq shopSettlementReq);
    }
}
