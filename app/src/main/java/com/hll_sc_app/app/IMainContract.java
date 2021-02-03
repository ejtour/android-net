package com.hll_sc_app.app;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/22
 */
interface IMainContract {
    interface IMainView extends ILoadView {
        void showFollowDialog(String qrcodeUrl);

        void handleOnlyReceive();
    }

    interface IMainPresenter extends IPresenter<IMainView>{

    }
}
