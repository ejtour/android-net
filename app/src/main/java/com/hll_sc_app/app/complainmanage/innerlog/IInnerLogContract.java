package com.hll_sc_app.app.complainmanage.innerlog;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainInnerLogResp;
import com.hll_sc_app.bean.complain.DropMenuBean;

import java.util.List;

public interface IInnerLogContract {
    interface IView extends ILoadView {
        String getComplaintID();

        void queryInnerLogSucess(ComplainInnerLogResp complainInnerLogResp);

        void queryDropMenusSuccess(List<DropMenuBean> departmentsBeanList);

        void saveLogSuccess();

        String getIssueDepartment();

        String getrelationDepartment();

        String getResult();
    }

    interface IPresent extends IPresenter<IView> {
        void queryInnerLog();
        void queryDropMenus();

        void saveComplainInnerLog();
    }


}
