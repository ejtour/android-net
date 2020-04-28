package com.hll_sc_app.app.order.summary;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public interface IOrderSummaryContract {
    interface IOrderSummaryView extends ILoadView {
        int getSearchType();

        String getSearchId();

        String getSearchWords();

        void setData(List<SummaryPurchaserBean> list, boolean append);
    }

    interface IOrderSummaryPresenter extends IPresenter<IOrderSummaryView> {
        void refresh();

        void loadMore();
    }
}
