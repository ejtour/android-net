package com.hll_sc_app.app.user.register;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.RegisterReq;

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
         * 去注册
         *
         * @param req 请求参数
         */
        void toRegister(RegisterReq req);

        /**
         * 上传图片
         *
         * @param file 图片文件
         */
        void uploadImg(File file);
    }
}
