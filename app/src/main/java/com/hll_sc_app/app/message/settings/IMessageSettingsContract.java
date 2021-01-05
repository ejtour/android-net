package com.hll_sc_app.app.message.settings;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.message.MessageSettingBean;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/28/20.
 */
interface IMessageSettingsContract {
    interface IMessageSettingsView extends ILoadView {
        void setData(List<MessageSettingBean> list);

        void saveSuccess();
    }

    interface IMessageSettingsPresenter extends IPresenter<IMessageSettingsView> {
        void save(List<String> types);
    }
}
