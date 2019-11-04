package com.hll_sc_app.app.setting.remind;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.RemindResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public interface IRemindSettingContract {
    interface IRemindSettingView extends ILoadView {
        void setData(RemindResp resp);

        void success();
    }

    interface IRemindSettingPresenter extends IPresenter<IRemindSettingView> {
        void update(boolean open, String times);
    }
}
