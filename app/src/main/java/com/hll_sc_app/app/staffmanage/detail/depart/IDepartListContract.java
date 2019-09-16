package com.hll_sc_app.app.staffmanage.detail.depart;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.staff.DepartmentListResp;

public interface IDepartListContract {
    interface IView extends ILoadView {
        void querySuccess(DepartmentListResp resp,boolean isMore);

        String getDepartName();

        void addSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void queryDepartments(boolean isLoading);

        void getMore();

        void refresh();

        int getPageSize();

        void addDepartment(String name);

    }
}
