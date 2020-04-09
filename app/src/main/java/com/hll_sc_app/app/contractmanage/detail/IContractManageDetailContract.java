package com.hll_sc_app.app.contractmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.contract.ContractProductListResp;

public interface IContractManageDetailContract {
    interface IView extends ILoadView {
        void deleteSuccess();

        void undateStatusSuccess(int status);

        void getProductSuccess(ContractProductListResp resp);
    }

    interface IPresent extends IPresenter<IView> {

        void delete(String id);

        void undateStatus(String id,int status);

        void getAlllProduct(String contractID);
    }
}
