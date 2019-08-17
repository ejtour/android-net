package com.hll_sc_app.app.invoice.detail.order;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.invoice.InvoiceOrderBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

public interface IRelevanceOrderContract {
    interface IRelevanceOrderView extends ILoadView {
        void setListData(List<InvoiceOrderBean> list, boolean append);
    }

    interface IRelevanceOrderPresenter extends IPresenter<IRelevanceOrderView> {
        void refresh();

        void loadMore();
    }
}
