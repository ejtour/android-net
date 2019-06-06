package com.hll_sc_app.app.user.register;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.io.File;

/**
 * 注册页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
public interface RegisterContract {

    interface IFindView extends ILoadView {
        /**
         * 注册成功
         */
        void registerSuccess();

        /**
         * 上传图片成功
         *
         * @param url 图片地址
         */
        void uploadSuccess(String url);
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

        /**
         * 上传图片
         *
         * @param file 图片文件
         */
        void uploadImg(File file);
    }
}
