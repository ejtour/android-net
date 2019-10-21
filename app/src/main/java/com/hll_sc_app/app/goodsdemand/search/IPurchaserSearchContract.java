package com.hll_sc_app.app.goodsdemand.search;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.window.NameValue;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public interface IPurchaserSearchContract {
    interface IPurchaserSearchView extends ILoadView {
        void setData(List<NameValue> list, boolean append);

        String getSearchWords();
    }

    interface IPurchaserSearchPresenter extends IPresenter<IPurchaserSearchView> {
        void refresh();

        void loadMore();
    }
}
