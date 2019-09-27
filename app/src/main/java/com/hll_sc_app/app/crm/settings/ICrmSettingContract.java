package com.hll_sc_app.app.crm.settings;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public interface ICrmSettingContract {
    interface ICrmSettingsView extends ILoadView {
        void logoutSuccess();
        void cleanSuccess();
        void startClean();
    }

    interface ICrmSettingPresenter extends IPresenter<ICrmSettingsView> {
        void logout();

        void cleanCache();
    }
}
