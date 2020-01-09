package com.hll_sc_app.app.order.summary.search;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/25
 */

public interface IOrderSummarySearchContract {
    interface IOrderSummarySearchView extends ISearchContract.ISearchView {
        int getIndex();
    }

    interface IOrderSummarySearchPresenter extends IPresenter<IOrderSummarySearchView> {
        void requestSearch(String searchWords);
    }
}
