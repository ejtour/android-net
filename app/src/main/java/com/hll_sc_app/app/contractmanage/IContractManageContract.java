package com.hll_sc_app.app.contractmanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.contract.ContractListResp;

public interface IContractManageContract {
    interface IView extends ILoadView {

        void querySuccess(ContractListResp resp, boolean isMore);

        String getSignTimeStart();

        String getSignTimeEnd();

        String getStatus();

        String getDays();

        String getContractCode();

        String getContractName();

        String getPurchaserName();

    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

        void queryMore();

        void refresh();

        int getPageSize();

    }
}
