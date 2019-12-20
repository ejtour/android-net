package com.hll_sc_app.app.staffmanage.salerole;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.staff.EmployeeBean;

import java.util.List;

/**
 * 员工列表——关联门店
 *
 */
public interface StaffSaleListContract {

    interface IView extends ILoadView {
        void showEmployeeList(List<EmployeeBean> employeeBeans, boolean isMore);

        String getSearchContent();
    }

    interface IPresent extends IPresenter<IView> {
        void queryEmployeeList(boolean isLoading);

        void refresh();

        void getMore();

        int getPageSize();
    }
}
