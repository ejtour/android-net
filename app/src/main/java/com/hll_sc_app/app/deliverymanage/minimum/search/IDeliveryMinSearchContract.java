package com.hll_sc_app.app.deliverymanage.minimum.search;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/24
 */
public interface IDeliveryMinSearchContract {
    interface IDeliveryMinSearchView extends ISearchContract.ISearchView{
        int getIndex();
    }

    interface IDeliveryMinSearchPresenter extends IPresenter<IDeliveryMinSearchView>{
        void requestSearch(String searchWords);
    }
}
