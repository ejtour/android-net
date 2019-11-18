package com.hll_sc_app.app.message.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.message.MessageDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public interface IMessageDetailContract {
    interface IMessageDetailView extends ILoadView {
        void setData(List<MessageDetailBean> list, boolean append);

        void success();
    }

    interface IMessageDetailPresenter extends IPresenter<IMessageDetailView> {
        void refresh();

        void loadMore();

        void markAsRead(String id);
    }
}
