package com.hll_sc_app.app.contractmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

public interface IContractManageDetailContract {
    interface IView extends ILoadView {
        void deleteSuccess();

        void checkSuccess();
    }

    interface IPresent extends IPresenter<IView> {

        void delete(String id);

        void check(String id);
    }
}
