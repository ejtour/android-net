package com.hll_sc_app.app.complainmanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainListResp;

public interface IComplainManageContract {

    interface IView extends ILoadView {
        void queryListSuccess(ComplainListResp resp,boolean isMore);

        int getComplaintStatus();

        void showCheckBox(boolean isCheck);

    }

    interface IPresent extends IPresenter<IView> {
        void queryComplainList(boolean isLoading);

        void getMore();

        void refresh();

        void export(String email);

        int getPageSize();
    }

}
