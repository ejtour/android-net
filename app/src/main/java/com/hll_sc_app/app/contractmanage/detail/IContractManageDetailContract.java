package com.hll_sc_app.app.contractmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

public interface IContractManageDetailContract {
    interface IView extends ILoadView {
        void deleteSuccess();

        void undateStatusSuccess(int status);
    }

    interface IPresent extends IPresenter<IView> {

        void delete(String id);

        void undateStatus(String id,int status);
    }
}
