package com.hll_sc_app.app.goodsdemand.commit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/22
 */

public interface IGoodsDemandCommitContract {
    interface IGoodsDemandCommitView extends ILoadView {
        void success();

        void showImg(String url);
    }

    interface IGoodsDemandCommitPresenter extends IPresenter<IGoodsDemandCommitView> {
        void commit(GoodsDemandReq req);

        void upload(String path);
    }
}
