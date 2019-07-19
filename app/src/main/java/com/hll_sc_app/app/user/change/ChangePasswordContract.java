package com.hll_sc_app.app.user.change;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 找回密码页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
public interface ChangePasswordContract {

    interface IChangeView extends ILoadView {
        /**
         * 修改密码成功
         */
        void changeSuccess();
    }

    interface IChangePresenter extends IPresenter<IChangeView> {
        /**
         * 修改密码
         *
         * @param loginPassword      旧密码
         * @param newLoginPassword   新密码
         * @param checkLoginPassword 确认密码
         */
        void toChange(String loginPassword, String newLoginPassword, String checkLoginPassword);
    }
}
