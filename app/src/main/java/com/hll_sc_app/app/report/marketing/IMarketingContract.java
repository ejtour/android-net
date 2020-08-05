package com.hll_sc_app.app.report.marketing;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.marketing.MarketingResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/27
 */

interface IMarketingContract {
    interface IMarketingView extends ILoadView {
        void setData(MarketingResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IMarketingPresenter extends IPresenter<IMarketingView> {
        void refresh();

        void loadMore();
    }
}
