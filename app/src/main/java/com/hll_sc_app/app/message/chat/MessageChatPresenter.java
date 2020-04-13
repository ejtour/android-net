package com.hll_sc_app.app.message.chat;

import android.app.Activity;

import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Message;
import com.hll_sc_app.rest.Upload;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageChatPresenter implements IMessageChatContract.IMessageChatPresenter {
    private IMessageChatContract.IMessageChatView mView;

    private MessageChatPresenter() {
    }

    public static MessageChatPresenter newInstance() {
        return new MessageChatPresenter();
    }

    @Override
    public void imageUpload(File file) {
        Upload.upload((BaseLoadActivity)mView,file.getAbsolutePath(), filepath -> {
            mView.uploadSuccess(filepath);
        });
    }

    @Override
    public void clearUnRead(String topic) {
        Message.clearUnreadMessage(topic, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                // no-op
            }
        });
    }

    @Override
    public void register(IMessageChatContract.IMessageChatView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
