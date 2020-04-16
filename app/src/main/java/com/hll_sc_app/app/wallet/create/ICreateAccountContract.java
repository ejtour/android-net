package com.hll_sc_app.app.wallet.create;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

public interface ICreateAccountContract {
    interface IView extends ILoadView {
        String getGroupName();

        void createSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void createAccount();
    }
}
