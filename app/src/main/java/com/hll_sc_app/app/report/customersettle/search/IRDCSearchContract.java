package com.hll_sc_app.app.report.customersettle.search;

import com.hll_sc_app.app.marketingsetting.product.IProductMarketingContract;
import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

interface IRDCSearchContract {
    interface IRDCSearchView extends ISearchContract.ISearchView {
        String getExtGroupID();
    }

    interface IRDCSearchPresenter extends IPresenter<IRDCSearchView>{
        void requestSearch(String searchWords);
    }
}
