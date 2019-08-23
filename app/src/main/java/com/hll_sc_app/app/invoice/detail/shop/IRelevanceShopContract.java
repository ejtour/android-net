package com.hll_sc_app.app.invoice.detail.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.invoice.InvoiceShopBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/23
 */

public interface IRelevanceShopContract {
    interface IRelevanceShopView extends ILoadView {
        void setListData(List<InvoiceShopBean> list, boolean append);
    }

    interface IRelevanceShopPresenter extends IPresenter<IRelevanceShopView> {
        void refresh();

        void loadMore();
    }
}
