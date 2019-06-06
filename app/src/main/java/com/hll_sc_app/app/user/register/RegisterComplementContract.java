package com.hll_sc_app.app.user.register;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.RegisterReq;

/**
 * 注册页面-完善资料
 *
 * @author zhuyingsong
 * @date 2019/6/6
 */
public interface RegisterComplementContract {

    interface IRegisterComplementView extends ILoadView {
        /**
         * 注册成功
         */
        void registerComplementSuccess();
    }

    interface IRegisterComplementPresenter extends IPresenter<IRegisterComplementView> {
        /**
         * 去注册
         *
         * @param req 请求参数
         */
        void toRegisterComplement(RegisterReq req);
    }
}
