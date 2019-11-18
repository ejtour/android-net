package com.hll_sc_app.app.message.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.message.MessageDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Message;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageDetailPresenter implements IMessageDetailContract.IMessageDetailPresenter {
    private IMessageDetailContract.IMessageDetailView mView;
    private int mPageNum;
    private int mCode;

    private MessageDetailPresenter(int code) {
        mCode = code;
    }

    public static MessageDetailPresenter newInstance(int code) {
        return new MessageDetailPresenter(code);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void markAsRead(String id) {
        Message.markAsRead(id, mCode, new SimpleObserver<Object>(mView, false) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Message.queryMessageDetail(mPageNum, mCode, new SimpleObserver<List<MessageDetailBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<MessageDetailBean> messageDetailBeans) {
                mView.setData(messageDetailBeans, mPageNum > 1);
                if (CommonUtils.isEmpty(messageDetailBeans)) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(IMessageDetailContract.IMessageDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
