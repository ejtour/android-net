package com.hll_sc_app.app.goodsdemand.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public interface IGoodsDemandDetailContract {
    interface IGoodsDemandDetailView extends ILoadView {
        void replySuccess();
    }

    interface IGoodsDemandDetailPresenter extends IPresenter<IGoodsDemandDetailView> {
        void reply(String content);
    }
}
