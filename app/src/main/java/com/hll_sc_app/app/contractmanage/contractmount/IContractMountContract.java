package com.hll_sc_app.app.contractmanage.contractmount;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.contract.ContractMountBean;
import com.hll_sc_app.bean.order.OrderResp;

import java.util.List;

public interface IContractMountContract {
    interface IView extends ILoadView {
        void getContractMountSuccess(ContractMountBean mountBean);

        void getOrderListSuccess(List<OrderResp> orderResps, boolean isMore);

        String getOrderNo();

        ContractListResp.ContractBean getContractBean();

        String getStartDate();

        String getEndDate();
    }

    interface IPresent extends IPresenter<IView> {
        void getContractMount(String contractID);

        void getOrderList(boolean isLoading);

        void getMoreOrder();

        void refreshOrder();

        int getOrderPageSize();
    }
}
