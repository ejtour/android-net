package com.hll_sc_app.app.setting.account.third;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/13
 */

interface IThirdAccountContract {
    interface IThirdAccountView extends ILoadView {
        void success();
    }

    interface IThirdAccountPresenter extends IPresenter<IThirdAccountView> {
        void unbind(int type);
    }
}
