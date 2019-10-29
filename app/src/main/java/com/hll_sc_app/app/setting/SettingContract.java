package com.hll_sc_app.app.setting;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author zhuyingsong
 * @date 2019/6/19
 */
public interface SettingContract {

    interface ISettingView extends ILoadView {
        /**
         * 退出登录成功
         */
        void logoutSuccess();
        void cleanSuccess();
        void startClean();
    }

    interface ISettingPresenter extends IPresenter<ISettingView> {
        /**
         * 退出登录
         */
        void logout();
        void cleanCache();
    }
}
