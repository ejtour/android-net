package com.hll_sc_app.app.user.find;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 找回密码页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
public interface FindPasswordContract {

    interface IFindView extends ILoadView {
        /**
         * 找回密码成功
         */
        void findSuccess();
    }

    interface IFindPresenter extends IPresenter<IFindView> {
        /**
         * 找回密码
         *
         * @param loginPhone    手机号
         * @param loginPWD      密码
         * @param checkCode     验证码
         * @param checkLoginPWD 确认密码
         */
        void toFind(String loginPhone, String checkCode, String loginPWD, String checkLoginPWD);
    }
}
