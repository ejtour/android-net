package com.hll_sc_app.app.message.notice;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/21/20.
 */
interface IMessageNoticeContract {
    interface IMessageNoticeView extends ILoadView {
        void success(String path);
    }

    interface IMessageNoticePresenter extends IPresenter<IMessageNoticeView> {
        void download(String url);
    }
}
