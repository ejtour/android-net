package com.hll_sc_app.app.setting;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public interface SettingActivityContract {

    interface ISaleUnitNameAddView extends ILoadView {
        /**
         * 退出登录成功
         */
        void logoutSuccess();
    }

    interface ISaleUnitNameAddPresenter extends IPresenter<ISaleUnitNameAddView> {
        /**
         * 退出登录
         */
        void logout();
    }
}
