package com.hll_sc_app.app.contractmanage.selectcontract;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.contract.ContractListResp;

import java.util.List;

interface ISelectContractListContract {

    interface IView extends ILoadView{
        void showList(List<ContractListResp.ContractBean> contractBeans, boolean isMore);

        String getContractName();
    }

    interface IPresent extends IPresenter<IView>{
        void queryList(boolean isLoading);

        void refresh();

        void quereMore();

    }
}
