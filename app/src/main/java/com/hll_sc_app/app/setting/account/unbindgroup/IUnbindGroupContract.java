package com.hll_sc_app.app.setting.account.unbindgroup;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 解绑集团
 *
 * @author zc
 */
public interface IUnbindGroupContract {
    interface IView extends ILoadView {
        /**
         * 解绑成功的回调
         */
        void unbindSuccess();
        /**
         * 取值输入的验证码
         */
        String getIdentifyCode();

        /**
         * 登出成功
         */
        void logoutSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        /**
         * 解绑集团
         */
        void unbindGroup();

        /**
         * 登出
         */
        void logout();
    }
}
