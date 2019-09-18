package com.hll_sc_app.app.stockmanage.customersendmanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.stockmanage.CustomerSendManageListResp;

import java.util.List;

public interface ICustomerSendManageContract {
    interface IView extends ILoadView {
        void getHouseListSuccess(List<HouseBean> houseBeans);

        String getSearchContent();

        String getHouseId();

        void queryCustomerSendManageListSuccess(CustomerSendManageListResp resp, boolean isMore);
    }

    interface IPresent extends IPresenter<IView> {
        void queryHouseList();

        void queryCustomerSendManageListResp(boolean isLoading);

        void getMore();

        void refresh();

        int getPageSize();

    }
}
