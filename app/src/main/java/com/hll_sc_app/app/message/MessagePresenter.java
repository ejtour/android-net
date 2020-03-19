package com.hll_sc_app.app.message;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Message;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessagePresenter implements IMessageContract.IMessagePresenter {
    private IMessageContract.IMessageView mView;
    private int mPageNum;

    private MessagePresenter() {
    }

    public static MessagePresenter newInstance() {
        return new MessagePresenter();
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
    public void loadSummary(boolean showLoading) {
        Message.queryMessageSummary(new SimpleObserver<List<MessageBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<MessageBean> messageBeans) {
                mView.setData(messageBeans);
            }
        });
    }

    @Override
    public void clearUnread() {
        Message.markAllAsRead(new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("已全部设为已读");
                mView.reload();
            }
        });
    }

    @Override
    public void start() {
        loadSummary(true);
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        if (UserConfig.crm()) return;
        Message.queryMessageList(mPageNum, new SimpleObserver<SingleListResp<MessageBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<MessageBean> messageBeanSingleListResp) {
                mView.setData(messageBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(messageBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(IMessageContract.IMessageView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
