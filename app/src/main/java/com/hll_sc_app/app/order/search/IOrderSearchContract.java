package com.hll_sc_app.app.order.search;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/25
 */

public interface IOrderSearchContract {
    interface IOrderSearchView extends ISearchContract.ISearchView {
        int getIndex();
    }

    interface IOrderSearchPresenter extends IPresenter<IOrderSearchView> {
        void requestSearch(String searchWords);
    }
}
