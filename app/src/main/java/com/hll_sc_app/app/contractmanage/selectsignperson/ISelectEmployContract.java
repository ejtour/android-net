package com.hll_sc_app.app.contractmanage.selectsignperson;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.staff.EmployeeBean;

import java.util.List;

public interface ISelectEmployContract {

    interface IView extends ILoadView {
        void querySuccess(List<EmployeeBean> employeeBeans, boolean isMore);

        String getSearchText();

    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

        void refresh();

        void quereMore();

    }
}
