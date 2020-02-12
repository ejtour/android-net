package com.hll_sc_app.app.contractmanage.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

public interface IContractManageAddContract {
    interface IView extends ILoadView {
        void addSuccess();

        String getActionType();

        String getAttachment();

        String getContractCode();

        String getContractName();

        String getEndDate();

        String getStartDate();

        String getSignDate();

        String getPurchaserID();

        String getPurchaserName();

        String getSignEmployeeName();

        String getSignEmployeeID();

        int getPurchaserType();

        String getRemarks();

    }

    interface IPresent extends IPresenter<IView> {
        void addContract();

    }
}
