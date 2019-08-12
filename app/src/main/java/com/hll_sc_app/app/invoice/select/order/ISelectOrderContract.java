package com.hll_sc_app.app.invoice.select.order;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.invoice.InvoiceOrderBean;
import com.hll_sc_app.bean.invoice.InvoiceOrderResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public interface ISelectOrderContract {
    interface ISelectOrderView extends ILoadView {
        void updateOrderData(InvoiceOrderResp resp,boolean isMore);
    }

    interface ISelectOrderPresenter extends IPresenter<ISelectOrderView> {
        void refresh();

        void loadMore();
    }
}
