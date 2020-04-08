package com.hll_sc_app.app.contractmanage.linkcontractlist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.contract.ContractListResp;

public interface ILinkContractListContract {
    interface IView extends ILoadView {

        void querySuccess(ContractListResp resp, boolean isMore);

        String getContractID();

        String getExContractID();
    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

        void queryMore();

        void refresh();

        int getPageSize();

    }
}
