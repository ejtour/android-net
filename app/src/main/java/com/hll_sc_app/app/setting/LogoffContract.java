package com.hll_sc_app.app.setting;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.http.SimpleObserver;

public interface LogoffContract {

    interface ILogoffView extends ILoadView {
        /**
         * 退出登录成功
         */
        void logoutSuccess();

    }

    interface ILogoffPresenter extends IPresenter<ILogoffView> {
        /**
         * 退出登录
         */
        void logout(SimpleObserver<Object> observer);

    }
}
