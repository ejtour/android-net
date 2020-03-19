package com.hll_sc_app.app.message;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.message.MessageBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public interface IMessageContract {
    interface IMessageView extends ILoadView {
        void setData(List<MessageBean> list, boolean append);

        void setData(List<MessageBean> list);

        void reload();
    }

    interface IMessagePresenter extends IPresenter<IMessageView> {
        void refresh();

        void loadMore();

        void loadSummary(boolean showLoading);

        void clearUnread();
    }
}
