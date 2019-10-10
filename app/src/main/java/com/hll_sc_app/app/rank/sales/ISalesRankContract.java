package com.hll_sc_app.app.rank.sales;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.rank.SalesRankResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public interface ISalesRankContract {
    interface ISalesRankView extends ILoadView {
        void setData(SalesRankResp resp,boolean append);
    }

    interface ISalesRankPresenter extends IPresenter<ISalesRankView> {
        void refresh();

        void loadMore();
    }
}
