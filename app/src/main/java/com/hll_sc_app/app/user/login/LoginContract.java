package com.hll_sc_app.app.user.login;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 登录页面
 *
 * @author zhuyingsong
 * @date 2019/6/4
 */
public interface LoginContract {

    interface ILoginView extends ILoadView {
        /**
         * 登录成功
         *
         * @param authType 角色类型
         */
        void loginSuccess(String authType);

        /**
         * 帐号拥有多个员工类型
         */
        void showChoiceDialog();
    }

    interface ILoginPresenter extends IPresenter<ILoginView> {
        /**
         * 登录
         *
         * @param loginPhone 手机号
         * @param loginPWD   密码
         * @param checkCode  验证码
         */
        void toLogin(String loginPhone, String loginPWD, String checkCode);
    }
}
