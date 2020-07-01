package com.hll_sc_app.app.message.chat;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public interface IMessageChatContract {
    interface IMessageChatView extends ILoadView {
        void uploadSuccess(String url);
    }

    interface IMessageChatPresenter extends IPresenter<IMessageChatView> {
        void imageUpload(String path);

        void clearUnRead(String topic);
    }
}
