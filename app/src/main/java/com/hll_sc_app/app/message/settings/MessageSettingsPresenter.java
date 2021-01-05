package com.hll_sc_app.app.message.settings;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.message.MessageSettingBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Message;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/28/20.
 */
class MessageSettingsPresenter implements IMessageSettingsContract.IMessageSettingsPresenter {
    private IMessageSettingsContract.IMessageSettingsView mView;

    public static MessageSettingsPresenter newInstance() {
        return new MessageSettingsPresenter();
    }

    private MessageSettingsPresenter() {
    }

    @Override
    public void save(List<String> types) {
        Message.saveMessageSettings(types, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void start() {
        Message.queryMessageSettings(new SimpleObserver<List<MessageSettingBean>>(mView) {
            @Override
            public void onSuccess(List<MessageSettingBean> messageSettingBeans) {
                mView.setData(messageSettingBeans);
            }
        });
    }

    @Override
    public void register(IMessageSettingsContract.IMessageSettingsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
