package com.hll_sc_app.app.setting.account.unbindmainaccount;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.account.UnbindMainAccountReq;

/**
 * 更改集团手机号
 *
 * @author zc
 */
public interface IUnbindMainAccountContract {
    interface IView extends ILoadView {

        /**
         * 获取校验验证码的结果
         */
        void getCheckIdentifyCodeResult(boolean isSuccess);

        /***
         * 更新下一步按钮的禁用可用状态
         */
        void updateNextStepButtonStatus(boolean isEnable);

        /**
         * 解绑结果
         */
        void getUnbindResult(boolean isSuccess);

        /***
         * 退出成功
         */
        void logoutSuccess();
    }

    interface IPresent extends IPresenter<IView> {
//        /**
//         * 验证每个步骤的验证码
//         *
//         * @param req
//         */
//        void checkIdentifyCode(CheckIdentifyCodeReq req);

        /**
         * 提交改变手机号的申请
         */
        void unBindMainAccount(UnbindMainAccountReq req);

        /**
         * 登出
         */
        void logout();
    }

    interface IFragment {
        /**
         * 停止倒计时
         */
        void stopCountdown();

        /**
         * 取值：用户手机号
         */
        String getPhoneNumber();
        /**
         * 取值：验证码
         */
        String getIdentifyCode();

        /**
         * 获取新密码
         */
        String getNewPassword();
        /**
         * 获取确认密码
         */
        String getCheckPassword();
    }
}
